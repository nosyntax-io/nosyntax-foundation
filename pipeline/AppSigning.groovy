pipeline {
  agent {
    label 'core'
  }

  parameters {
    string defaultValue: '', name: 'ACCESS_TOKEN'
    string defaultValue: '', name: 'PROJECT_ID'
    string defaultValue: '', name: 'APP_NAME'
  }

  environment {
    ACCESS_TOKEN = "${params.ACCESS_TOKEN}"
    PROJECT_ID = "${params.PROJECT_ID}"
    APP_NAME = "${params.APP_NAME}"
  }

  stages {
    stage('Prepare Workspace') {
      steps {
        cleanWs()
      }
    }

    stage('Generate Keystore') {
      steps {
        script {
          def signingKeyAlias = APP_NAME.replaceAll('[^a-zA-Z0-9 ]', '').replaceAll(' ', '_').toLowerCase()
          def signingKeyPassword = sh(script: 'openssl rand -base64 16', returnStdout: true).trim()

          env.KEYSTORE_PASSWORD = signingKeyPassword
          env.KEY_ALIAS = signingKeyAlias
          env.KEY_PASSWORD = signingKeyPassword

          sh """
            keytool -genkey -v -keystore signing.keystore -alias ${KEY_ALIAS} \\
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
          def keystoreOutput = sh(script: "keytool -list -v -keystore signing.keystore -alias ${KEY_ALIAS} -storepass ${KEYSTORE_PASSWORD}", returnStdout: true).trim()
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
          def destinationDirectory = '/var/www/cloud.mynta.app/repository/keystores'
          sh "mv signing.keystore ${destinationDirectory}/${PROJECT_ID}.keystore"
        }
      }
    }

    stage('Send API Request') {
      steps {
        script {
          try {
            withCredentials([string(credentialsId: 'API_SECRET_KEY', variable: 'API_SECRET_KEY')]) {
              def url = 'https://api.mynta.app/cloud/request_update_project_signing_config.inc.php'

              def postData = [
                api_secret_key    : API_SECRET_KEY,
                access_token      : ACCESS_TOKEN,
                sha1_fingerprint  : SHA1_FINGERPRINT,
                sha256_fingerprint: SHA256_FINGERPRINT,
                keystore_password : KEYSTORE_PASSWORD,
                key_alias         : KEY_ALIAS,
                key_password      : KEY_PASSWORD
              ]

              def response = httpRequest(
                url: url,
                httpMode: 'POST',
                contentType: 'APPLICATION_JSON',
                requestBody: groovy.json.JsonOutput.toJson(postData)
              )
            }
          } catch (Exception e) {
            currentBuild.result = 'FAILURE'
            error "Failed to send API request: ${e.message}"
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