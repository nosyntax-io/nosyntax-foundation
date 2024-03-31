pipeline {
  agent {
    label 'core'
  }

  parameters {
    string defaultValue: '', name: 'PROJECT_ID'
    string defaultValue: '', name: 'ACCESS_TOKEN'
    string defaultValue: '', name: 'APP_NAME'
  }

  environment {
    PROJECT_ID = "${params.PROJECT_ID}"
    ACCESS_TOKEN = "${params.ACCESS_TOKEN}"
    APP_NAME = "${params.APP_NAME}"
  }

  stages {
    stage('Generate Keystore') {
      steps {
        script {
          def keyAlias = APP_NAME.replaceAll('[^a-zA-Z0-9 ]', '').replaceAll(' ', '_').toLowerCase()
          def keyPassword = sh(script: 'openssl rand -base64 16', returnStdout: true).trim()

          env.KEYSTORE_FILE = 'signing.keystore'
          env.KEYSTORE_PASSWORD = keyPassword
          env.KEY_ALIAS = keyAlias
          env.KEY_PASSWORD = keyPassword

          sh """
            keytool -genkey -v -keystore ${KEYSTORE_FILE} -alias ${KEY_ALIAS} \\
              -keyalg RSA -keysize 2048 -validity 10000 \\
              -storepass ${KEYSTORE_PASSWORD} -keypass ${KEY_PASSWORD} \\
              -dname \"O=${APP_NAME}\"
          """
        }
      }
    }

    stage('Extract Certificate Fingerprints') {
      steps {
        script {
          def keystoreOutput = sh(
            script: "keytool -list -v -keystore ${KEYSTORE_FILE} -alias ${KEY_ALIAS} -storepass ${KEYSTORE_PASSWORD}",
            returnStdout: true
          ).trim()

          try {
            def sha1Fingerprint = (keystoreOutput =~ /SHA1: ([A-F0-9:]+)/)[0][1]
            env.SHA1_FINGERPRINT = sha1Fingerprint

            def sha256Fingerprint = (keystoreOutput =~ /SHA256: ([A-F0-9:]+)/)[0][1]
            env.SHA256_FINGERPRINT = sha256Fingerprint
          } catch (Exception e) {
            currentBuild.result = 'FAILURE'
            error "Failed to extract certificate fingerprints: ${e.message}"
          }
        }
      }
    }

    stage('Package Keystore') {
      steps {
        script {
          def keystoreDirectory = '/var/www/cloud.nosyntax.io/repository/keystores'
          sh "mv ${KEYSTORE_FILE} ${keystoreDirectory}/${PROJECT_ID}.keystore"
        }
      }
    }

    stage('Update Signing Config') {
      steps {
        script {
          try {
            withCredentials([string(credentialsId: 'API_KEY', variable: 'API_KEY')]) {
              def url = 'https://api.nosyntax.io/cloud/update_signing_config.inc.php'

              def postData = [
                access_token      : ACCESS_TOKEN,
                keystore_file     : KEYSTORE_FILE,
                keystore_password : KEYSTORE_PASSWORD,
                key_alias         : KEY_ALIAS,
                key_password      : KEY_PASSWORD,
                sha1_fingerprint  : SHA1_FINGERPRINT,
                sha256_fingerprint: SHA256_FINGERPRINT
              ]
              def encodedData = postData.collect { k, v ->
                "${URLEncoder.encode(k.toString(), 'UTF-8')}=${URLEncoder.encode(v.toString(), 'UTF-8')}"
              }.join('&')

              httpRequest(
                url: url,
                httpMode: 'POST',
                contentType: 'APPLICATION_FORM',
                requestBody: encodedData,
                validResponseCodes: '200:500',
                customHeaders: [
                  [name: 'API-KEY', value: API_KEY, maskValue: true]
                ]
              )
            }
          } catch (Exception e) {
            currentBuild.result = 'FAILURE'
            error "Failed to update signing configuration: ${e.message}"
          }
        }
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}