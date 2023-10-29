pipeline {
  agent any

  tools {
    gradle 'Gradle 8.0'
  }

  libraries {
    lib('android-pipeline-library')
  }

  parameters {
    string(defaultValue: '', name: 'BUILD_ID')
    string(defaultValue: '', name: 'BUILD_ENVIRONMENT')
    string(defaultValue: '', name: 'USER_TOKEN')
    string(defaultValue: '', name: 'SERVER_ACCESS_TOKEN')
    string(defaultValue: '', name: 'PROJECT_ID')
    string(defaultValue: '', name: 'APP_ID')
    string(defaultValue: '', name: 'APP_NAME')
    string(defaultValue: '', name: 'APP_VERSION_NUMBER')
    string(defaultValue: '', name: 'APP_VERSION_NAME')
    string(defaultValue: '', name: 'APP_REMOTE_CONFIG')
    string(defaultValue: '', name: 'ONESIGNAL_APP_ID')
    booleanParam(defaultValue: false, name: 'IS_MONETIZE')
    string(defaultValue: '', name: 'ADMOB_APP_ID')
    string(defaultValue: '', name: 'ADMOB_BANNER_ID')
    string(defaultValue: '', name: 'ADMOB_INTERSTITIAL_ID')
    string(defaultValue: '', name: 'KEYSTORE_FILE')
    string(defaultValue: '', name: 'KEYSTORE_PASSWORD')
    string(defaultValue: '', name: 'KEY_ALIAS')
    string(defaultValue: '', name: 'KEY_PASSWORD')
    string(defaultValue: '', name: 'LOCAL_APP_CONFIG')
  }

  environment {
    BUILD_ID = "${params.BUILD_ID}"
    BUILD_ENVIRONMENT = "${params.BUILD_ENVIRONMENT}"
    USER_TOKEN = "${params.USER_TOKEN}"
    SERVER_ACCESS_TOKEN = "${params.SERVER_ACCESS_TOKEN}"
    PROJECT_ID = "${params.PROJECT_ID}"
    APP_ID = "${params.APP_ID}"
    APP_NAME = "${params.APP_NAME}"
    APP_VERSION_NUMBER = "${params.APP_VERSION_NUMBER}"
    APP_VERSION_NAME = "${params.APP_VERSION_NAME}"
    APP_REMOTE_CONFIG = "${params.APP_REMOTE_CONFIG}"
    ONESIGNAL_APP_ID = "${params.ONESIGNAL_APP_ID}"
    ADMOB_APP_ID = "${params.ADMOB_APP_ID}"
    ADMOB_BANNER_ID = "${params.ADMOB_BANNER_ID}"
    ADMOB_INTERSTITIAL_ID = "${params.ADMOB_INTERSTITIAL_ID}"
    KEYSTORE_FILE = "${params.KEYSTORE_FILE}"
    KEYSTORE_PASSWORD = "${params.KEYSTORE_PASSWORD}"
    KEY_ALIAS = "${params.KEY_ALIAS}"
    KEY_PASSWORD = "${params.KEY_PASSWORD}"
    LOCAL_APP_CONFIG = "${params.LOCAL_APP_CONFIG}"
    REPOSITORY_PATH = '/var/www/cloud.mynta.app/repository'
  }

  stages {
    stage('Checkout Source Code') {
      steps {
        script {
          checkout scm
        }
      }
    }

    stage('Configure Application') {
      parallel {
        stage('Set App Properties') {
          steps {
            script {
              def propertyMap = [
                'PARAM_BUILD_ENVIRONMENT': 'BUILD_ENVIRONMENT',
                'PARAM_APP_ID': 'APP_ID',
                'PARAM_APP_NAME': 'APP_NAME',
                'PARAM_APP_VERSION_NUMBER': 'APP_VERSION_NUMBER',
                'PARAM_APP_VERSION_NAME': 'APP_VERSION_NAME',
                'PARAM_APP_REMOTE_CONFIG': 'APP_REMOTE_CONFIG',
                'PARAM_SERVER_ACCESS_TOKEN': 'SERVER_ACCESS_TOKEN',
                'PARAM_ONESIGNAL_APP_ID': 'ONESIGNAL_APP_ID',
                'PARAM_ADMOB_APP_ID': 'ADMOB_APP_ID',
                'PARAM_ADMOB_BANNER_ID': 'ADMOB_BANNER_ID',
                'PARAM_ADMOB_INTERSTITIAL_ID': 'ADMOB_INTERSTITIAL_ID'
              ]
              def templateSourcePath = "${WORKSPACE}/local.properties.template"
              def outputSourceDestination = "${WORKSPACE}/local.properties"

              setTemplateProperties(propertyMap, templateSourcePath, outputSourceDestination)
            }
          }
        }

        stage('Manage App Local Config') {
          steps {
            script {
              def appConfigDirectory = "${WORKSPACE}/app/src/main/assets/local"
              def appConfigPath = "${appConfigDirectory}/app_config.json"

              if (!fileExists(appConfigDirectory)) {
                sh "mkdir -p ${appConfigDirectory}"
              }
              writeFile file: appConfigPath, text: env.APP_REMOTE_CONFIG ? env.LOCAL_APP_CONFIG : ''
            }
          }
        }

        stage('Copy Google Services Config') {
          steps {
            script {
              def googleServicesSourcePath = "${REPOSITORY_PATH}/assets/google_services/${PROJECT_ID}.json"
              def googleServicesDestination = "${WORKSPACE}/app/google-services.json"

              if (fileExists(googleServicesSourcePath)) {
                sh "cp -f ${googleServicesSourcePath} ${googleServicesDestination}"
              } else {
                def propertyMap = [
                  'PARAM_PACKAGE_NAME': 'APP_ID'
                ]
                def defaultGoogleServicesDefaultPath = "${REPOSITORY_PATH}/assets/google_services/default.json"
                setTemplateProperties(propertyMap, defaultGoogleServicesDefaultPath, googleServicesDestination)
              }
            }
          }
        }

        stage('Generate Launcher Icons') {
          steps {
            script {
              def defaultIconSourcePath = "${REPOSITORY_PATH}/assets/launcher_icons/default.png"
              def specificIconSourcePath = "${REPOSITORY_PATH}/assets/launcher_icons/${PROJECT_ID}.png"
              def resDirectory = "${WORKSPACE}/app/src/main/res"

              def iconSourcePath = fileExists(specificIconSourcePath) ? specificIconSourcePath : defaultIconSourcePath

              generateLauncherIcons(resDirectory, iconSourcePath)
            }
          }
        }

        stage('Generate Icon Assets') {
          steps {
            script {
              def defaultIconSourcePath = "${REPOSITORY_PATH}/assets/app_icons/default.png"
              def specificIconSourcePath = "${REPOSITORY_PATH}/assets/app_icons/${PROJECT_ID}.png"
              def resDirectory = "${WORKSPACE}/app/src/main/res"

              def iconSourcePath = fileExists(specificIconSourcePath) ? specificIconSourcePath : defaultIconSourcePath

              generateIconAssets(resDirectory, iconSourcePath)
            }
          }
        }
      }
    }

    stage('Manage Application Sourcecode') {
      steps {
        script {
          archiveArtifacts(
            artifacts: '**/*',
            allowEmptyArchive: true,
            excludes: '''
              **/.apk,
              **/.git/,
              **/.gradle/,
              **/*.jks,
              **/*.keystore,
              **/*.log,
              **/*.md,
              **/*.template,
              **/build/,
              **/pipeline/
            '''
          )
        }
      }
    }

    stage('Manage Application Signing') {
      stages {
        stage('Obtain Signing Key') {
          steps {
            script {
              def signingKeyPath = "${REPOSITORY_PATH}/keystores/${PROJECT_ID}.keystore"

              if (!fileExists(signingKeyPath)) {
                build job: 'AppSigning', parameters: [
                  string(name: 'ACCESS_TOKEN', value: env.SERVER_ACCESS_TOKEN),
                  string(name: 'PROJECT_ID', value: env.PROJECT_ID),
                  string(name: 'APP_NAME', value: env.APP_NAME)
                ]
              } else {
                sh "cp -f ${signingKeyPath} ${WORKSPACE}/signing.keystore"
              }
            }
          }
        }

        stage('Set Signing Properties') {
          steps {
            script {
              try {
                def propertyMap = [
                  'PARAM_SIGNING_KEYSTORE_FILE': 'KEYSTORE_FILE',
                  'PARAM_SIGNING_KEYSTORE_PASSWORD': 'KEYSTORE_PASSWORD',
                  'PARAM_SIGNING_KEY_ALIAS': 'KEY_ALIAS',
                  'PARAM_SIGNING_KEY_PASSWORD': 'KEY_PASSWORD'
                ]
                def templateSourcePath = "${WORKSPACE}/signing.properties.template"
                def outputDestination = "${WORKSPACE}/signing.properties"

                setTemplateProperties(propertyMap, templateSourcePath, outputDestination)
              } catch (Exception ex) {
                currentBuild.result = 'FAILURE'
                error "Error in Set Signing Properties stage: ${ex.getMessage()}"
              }
            }
          }
        }
      }
    }

    stage('Build Release Artifact') {
      steps {
        script {
          try {
            sh 'chmod +rx gradlew'

            def buildFlavor = params.IS_MONETIZE ? "monetize" : "regular"

            def assembleTask = "assemble${buildFlavor.capitalize()}"
            def bundleTask = "bundle${buildFlavor.capitalize()}"

            sh "./gradlew ${assembleTask} ${bundleTask}"

            def apkSourcePath = "${WORKSPACE}/app/build/outputs/apk/${buildFlavor}/release/app-${buildFlavor}-release.apk"
            def aabSourcePath = "${WORKSPACE}/app/build/outputs/bundle/${buildFlavor}Release/app-${buildFlavor}-release.aab"

            def apkDestinationPath = "${REPOSITORY_PATH}/outputs/apk/${PROJECT_ID}.apk"
            def aabDestinationPath = "${REPOSITORY_PATH}/outputs/aab/${PROJECT_ID}.aab"

            sh "mv ${apkSourcePath} ${apkDestinationPath}"
            sh "mv ${aabSourcePath} ${aabDestinationPath}"
          } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            error "Error in Build Release Artifact stage: ${ex.getMessage()}"
          }
        }
      }
    }

    stage('Approve Repository Access') {
      steps {
        script {
          approveRepositoryAccess("apk")
          approveRepositoryAccess("aab")
        }
      }
    }
  }

  post {
    success {
      addBuildHistory(1)
    }
    unsuccessful {
      addBuildHistory(-1)
    }
  }
}

def approveRepositoryAccess(fileType) {
  try {
    withCredentials([string(credentialsId: 'REPO_BASIC_AUTH', variable: 'REPO_BASIC_AUTH')]) {
      def url = "https://cloud.mynta.app/repository/request_repository_access_approval.serv.php"

      def authString = "${REPO_BASIC_AUTH}"
      def authEncoded = authString.getBytes('UTF-8').encodeBase64().toString()

      def targetFile = "${APP_ID}.${fileType}"
      def postData = [
        target_file: targetFile,
        type: fileType
      ]

      def response = httpRequest(
        url: url,
        httpMode: 'POST',
        contentType: 'APPLICATION_JSON',
        requestBody: writeJSON(
          returnText: true, json: postData
        ),
        validResponseCodes: '200:500',
        customHeaders: [
          [name: 'Authorization', value: "Basic ${authEncoded}"]
        ]
      )

      if (response.status != 200) {
        currentBuild.result = 'FAILURE'
        error "API request failed. Status code: ${response.status}. Response body: ${response.response}"
      }

      def responseBody = readJSON text: response.getContent()
      switch (fileType) {
      case "apk":
        env.APK_FILE_TOKEN = responseBody.token
        break
      case "aab":
        env.AAB_FILE_TOKEN = responseBody.token
        break
      default:
        echo "Unsupported file type: ${fileType}"
      }
    }
  } catch (Exception e) {
    currentBuild.result = 'FAILURE'
    error "Failed to approve repository access: ${e.message}"
  }
}

def addBuildHistory(int buildStatus) {
  try {
    withCredentials([string(credentialsId: 'API_SECRET_KEY', variable: 'API_SECRET_KEY')]) {
      def url = "https://api.mynta.app/cloud/request_add_build_history.inc.php"

      def postData = [
        api_secret_key: API_SECRET_KEY,
        user_token: USER_TOKEN,
        access_token: SERVER_ACCESS_TOKEN,
        build_id: BUILD_ID,
        build_status: buildStatus,
        version_number: APP_VERSION_NUMBER
      ]

      if (buildStatus == 1) {
        postData += [
          apk_file_token: APK_FILE_TOKEN,
          aab_file_token: AAB_FILE_TOKEN
        ]
      }

      def response = httpRequest(
        url: url,
        httpMode: 'POST',
        contentType: 'APPLICATION_JSON',
        requestBody: writeJSON(
          returnText: true, json: postData
        ),
        validResponseCodes: '200:500'
      )

      if (response.status != 200) {
        currentBuild.result = 'FAILURE'
        error "API request failed with status code: ${response.status}"
      }
    }
  } catch (Exception e) {
    currentBuild.result = 'FAILURE'
    error "Failed to send API request: ${e.message}"
  }
}