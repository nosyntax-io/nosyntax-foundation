pipeline {
  agent any

  tools {
    gradle 'Gradle 8.0'
  }

  libraries {
    lib('android-pipeline-library')
  }

  parameters {
    string defaultValue: '', name: 'BUILD_ID'
    string defaultValue: '', name: 'BUILD_ENVIRONMENT'
    string defaultValue: '', name: 'USER_TOKEN'
    string defaultValue: '', name: 'ACCESS_TOKEN'
    string defaultValue: '', name: 'APP_ID'
    string defaultValue: '', name: 'APP_NAME'
    string defaultValue: '', name: 'VERSION_NUMBER'
    string defaultValue: '', name: 'VERSION_NAME'
    string defaultValue: '', name: 'ONESIGNAL_APP_ID'
    string defaultValue: '', name: 'ADMOB_APP_ID'
    string defaultValue: '', name: 'ADMOB_INTERSTITIAL_ID'
    // keystore credentials
    string defaultValue: '', name: 'KEYSTORE_FILE'
    string defaultValue: '', name: 'KEYSTORE_PASSWORD'
    string defaultValue: '', name: 'KEY_ALIAS'
    string defaultValue: '', name: 'KEY_PASSWORD'
  }

  environment {
    BUILD_ID = "${params.BUILD_ID}"
    BUILD_ENVIRONMENT = "${params.BUILD_ENVIRONMENT}"
    USER_TOKEN = "${params.USER_TOKEN}"
    ACCESS_TOKEN = "${params.ACCESS_TOKEN}"
    APP_ID = "${params.APP_ID}"
    APP_NAME = "${params.APP_NAME}"
    VERSION_NUMBER = "${params.VERSION_NUMBER}"
    VERSION_NAME = "${params.VERSION_NAME}"
    ONESIGNAL_APP_ID = "${params.ONESIGNAL_APP_ID}"
    ADMOB_APP_ID = "${params.ADMOB_APP_ID}"
    ADMOB_INTERSTITIAL_ID = "${params.ADMOB_INTERSTITIAL_ID}"
    KEYSTORE_FILE = "${params.KEYSTORE_FILE}"
    KEYSTORE_PASSWORD = "${params.KEYSTORE_PASSWORD}"
    KEY_ALIAS = "${params.KEY_ALIAS}"
    KEY_PASSWORD = "${params.KEY_PASSWORD}"

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
        stage('Set Application Properties') {
          steps {
            script {
              def propertyMap = [
                'PARAM_BUILD_ENVIRONMENT': 'BUILD_ENVIRONMENT',
                'PARAM_APP_ID': 'APP_ID',
                'PARAM_APP_NAME': 'APP_NAME',
                'PARAM_APP_VERSION_NUMBER': 'VERSION_NUMBER',
                'PARAM_APP_VERSION_NAME': 'VERSION_NAME',
                'PARAM_SERVER_ACCESS_TOKEN': 'ACCESS_TOKEN',
                'PARAM_ONESIGNAL_APP_ID': 'ONESIGNAL_APP_ID',
                'PARAM_ADMOB_APP_ID': 'ADMOB_APP_ID',
                'PARAM_ADMOB_INTERSTITIAL_ID': 'ADMOB_INTERSTITIAL_ID'
              ]
              def templateSourcePath = "${WORKSPACE}/local.properties.template"
              def outputSourceDestination = "${WORKSPACE}/local.properties"

              setTemplateProperties(propertyMap, templateSourcePath, outputSourceDestination)
            }
          }
        }

        stage('Copy Google Services Config') {
          steps {
            script {
              try {
                def googleServicesSourcePath = "${REPOSITORY_PATH}/assets/google_services/${APP_ID}.json"
                def googleServicesDestination = "${WORKSPACE}/app/google-services.json"

                sh "cp -f ${googleServicesSourcePath} ${googleServicesDestination}"
              } catch (Exception ex) {
                currentBuild.result = 'FAILURE'
                error "Error in Copy Google Services Config stage: ${ex.getMessage()}"
              }
            }
          }
        }

        stage('Generate Launcher Icons') {
          steps {
            script {
              def defaultIconSourcePath = "${REPOSITORY_PATH}/assets/app_icons/default.png"
              def specificIconSourcePath = "${REPOSITORY_PATH}/assets/app_icons/${APP_ID}.png"
              def resDirectory = "${WORKSPACE}/app/src/main/res"

              def iconSourcePath = fileExists(specificIconSourcePath) ? specificIconSourcePath : defaultIconSourcePath

              generateLauncherIcons(resDirectory, iconSourcePath)
            }
          }
        }

        stage('Generate Logo Assets') {
          steps {
            script {
              def defaultLogoSourcePath = "${REPOSITORY_PATH}/assets/app_logos/default.png"
              def specificLogoSourcePath = "${REPOSITORY_PATH}/assets/app_logos/${APP_ID}.png"
              def resDirectory = "${WORKSPACE}/app/src/main/res"

              def logoSourcePath = fileExists(specificLogoSourcePath) ? specificLogoSourcePath : defaultLogoSourcePath

              generateLogoAssets(resDirectory, logoSourcePath)
            }
          }
        }
      }
    }

    stage('Manage Application Signing') {
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

            def keystoreSourcePath = "${REPOSITORY_PATH}/keystores/keystore.${APP_ID}.zip"
            sh "unzip -o ${keystoreSourcePath} -d ${WORKSPACE}"
          } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            error "Error in Manage Signing Configuration stage: ${ex.getMessage()}"
          }
        }
      }
    }

    stage('Build Release Artifact') {
      steps {
        script {
          try {
            def outputsPath = "${WORKSPACE}/app/build/outputs"
            def buildEnvironment = env.BUILD_ENVIRONMENT

            sh 'chmod +rx gradlew'

            if (buildEnvironment == "production") {
              sh "./gradlew assembleRelease bundleRelease"
            } else {
              sh "./gradlew assembleRelease"
            }

            def apkSourcePath = "${outputsPath}/apk/release/app-release.apk"
            def apkDestinationPath = "${REPOSITORY_PATH}/outputs/apk/${APP_ID}.apk"

            sh "mv ${apkSourcePath} ${apkDestinationPath}"
            env.APK_FILE_SIZE = sh(script: "du -sh ${apkDestinationPath} | cut -f1", returnStdout: true).trim()

            if (buildEnvironment == "production") {
              def aabSourcePath = "${outputsPath}/bundle/release/app-release.aab"
              def aabDestinationPath = "${REPOSITORY_PATH}/outputs/aab/${APP_ID}.aab"

              sh "mv ${aabSourcePath} ${aabDestinationPath}"
              env.AAB_FILE_SIZE = sh(script: "du -sh ${aabDestinationPath} | cut -f1", returnStdout: true).trim()
            }
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
          if (BUILD_ENVIRONMENT == "production") {
            approveRepositoryAccess("aab")
          }
        }
      }
    }
  }

  post {
    unsuccessful {
      cleanWs()
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
        requestBody: groovy.json.JsonOutput.toJson(postData),
        validResponseCodes: '200:500',
        customHeaders: [
          [name: 'Authorization', value: "Basic ${authEncoded}"]
        ]
      )

      if (response.status != 200) {
        currentBuild.result = 'FAILURE'
        error "API request failed. Status code: ${response.status}. Response body: ${response.response}"
      }

      def responseBody = new groovy.json.JsonSlurper().parseText(response.getContent())
      echo "Result: ${responseBody.token}"
    }
  } catch (Exception e) {
    currentBuild.result = 'FAILURE'
    error "Failed to approve repository access: ${e.message}"
  }
}