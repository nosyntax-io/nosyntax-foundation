pipeline {
  agent {
    label 'core'
  }

  parameters {
    string defaultValue: '', name: 'PROJECT_ID'
  }

  environment {
    PROJECT_ID = "${params.PROJECT_ID}"
  }

  stages {
    stage('Generate Keystore') {
      steps {
        script {
          try {
            def password = sh(script: 'openssl rand -base64 16', returnStdout: true).trim()

            env.KEYSTORE_FILE = 'signing.keystore'
            env.KEYSTORE_PASSWORD = password
            env.KEY_ALGORITHM = 'RSA'
            env.KEY_SIZE = 2048
            env.KEY_VALIDITY = 10000
            env.KEY_ALIAS = env.PROJECT_ID
            env.KEY_PASSWORD = password

            sh """
              keytool -genkey -v -keystore ${KEYSTORE_FILE} -alias ${KEY_ALIAS} \\
                -keyalg ${KEY_ALGORITHM} -keysize ${KEY_SIZE} -validity ${KEY_VALIDITY} \\
                -storepass ${KEYSTORE_PASSWORD} -keypass ${KEY_PASSWORD} \\
                -dname \"O=${PROJECT_ID}\"
            """
          } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            error "Failed to generate keystore: ${ex.getMessage()}"
          }
        }
      }
    }

    stage('Extract Certificate Fingerprints') {
      steps {
        script {
          try {
            def keystoreOutput = sh(
              script: "keytool -list -v -keystore ${KEYSTORE_FILE} -alias ${KEY_ALIAS} -storepass ${KEYSTORE_PASSWORD}",
              returnStdout: true
            ).trim()

            def sha1Fingerprint = (keystoreOutput =~ /SHA1: ([A-F0-9:]+)/)[0][1]
            env.SHA1_FINGERPRINT = sha1Fingerprint

            def sha256Fingerprint = (keystoreOutput =~ /SHA256: ([A-F0-9:]+)/)[0][1]
            env.SHA256_FINGERPRINT = sha256Fingerprint
          } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            error "Failed to extract certificate fingerprints: ${ex.getMessage()}"
          }
        }
      }
    }

    stage('Move Keystore to Repository') {
      steps {
        script {
          def keystoreDirectory = '/var/www/cloud.nosyntax.io/repository/keystores'
          def storedKeystorePath = "${keystoreDirectory}/${PROJECT_ID}.keystore"

          try {
            sh "mv ${KEYSTORE_FILE} ${storedKeystorePath}"
          } catch (Exception e) {
            currentBuild.result = 'FAILURE'
            error "Failed to move keystore file to ${storedKeystorePath}: ${e.getMessage()}"
          }
        }
      }
    }

    stage('Update Signing Settings') {
      steps {
        script {
          try {
            withCredentials([string(credentialsId: 'API_KEY', variable: 'API_KEY')]) {
              def url = 'https://api.nosyntax.io/cloud/update_signing_settings.php'

              def postData = [
                project_id        : PROJECT_ID,
                keystore_file     : KEYSTORE_FILE,
                keystore_password : KEYSTORE_PASSWORD,
                key_algorithm     : KEY_ALGORITHM,
                key_size          : KEY_SIZE,
                key_validity      : KEY_VALIDITY,
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
          } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            error "Failed to update signing settings: ${ex.getMessage()}"
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