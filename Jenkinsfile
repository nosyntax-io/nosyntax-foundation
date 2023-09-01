pipeline {
	agent any

  libraries {
		lib('android-pipeline-library')
	}

  parameters {
		string defaultValue: '', name: 'BUILD_ID'
		string defaultValue: '', name: 'USER_TOKEN'
		string defaultValue: '', name: 'ACCESS_TOKEN'
		string defaultValue: '', name: 'APP_NAME'
		string defaultValue: '', name: 'PACKAGE_NAME'
		string defaultValue: '', name: 'VERSION_NAME'
		string defaultValue: '', name: 'VERSION_CODE'
		string defaultValue: '', name: 'ONESIGNAL_APP_ID'
	}

	environment {
    USER_TOKEN              = "${params.USER_TOKEN}"
    ACCESS_TOKEN            = "${params.ACCESS_TOKEN}"
    APP_NAME                = "${params.APP_NAME}"
    PACKAGE_NAME            = "${params.PACKAGE_NAME}"
    VERSION_NAME            = "${params.VERSION_NAME}"
    VERSION_CODE            = "${params.VERSION_CODE}"
    ONESIGNAL_APP_ID        = "${params.ONESIGNAL_APP_ID}"
	}

	stages {
		stage('Checkout Source Code') {
			steps {
				script {
					checkout scm
				}
			}
		}

    stage('Set Application Properties') {
      steps {
        script {
          def propertyMap = [
            'PARAMETER_APP_NAME': 'APP_NAME',
            'PARAMETER_APP_PACKAGE_NAME': 'PACKAGE_NAME',
            'PARAMETER_APP_VERSION_NAME': 'VERSION_NAME',
            'PARAMETER_APP_VERSION_CODE': 'VERSION_CODE',
            'PARAMETER_APP_ACCESS_TOKEN': 'ACCESS_TOKEN',
            'PARAMETER_APP_ONESIGNAL_APP_ID': 'ONESIGNAL_APP_ID'
          ]
          def templateSourcePath = "${WORKSPACE}/local.properties.template"
          def outputSourceDestination = "${WORKSPACE}/local.properties"

          setTemplateProperties(propertyMap, templateSourcePath, outputSourceDestination)
        }
      }
    }
	}
}