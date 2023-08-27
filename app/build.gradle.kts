plugins {
    Libraries.plugins.forEach { id(it) }
}

val appConfig = Properties().load(rootProject.file("local.properties"))
val signingConfig = Properties().load(rootProject.file("signing.properties"))

android {
    namespace = "app.mynta.template.android"
    compileSdk = 33

    defaultConfig {
        applicationId = appConfig.getProperty(AppConfig.APP_PACKAGE_NAME)
        versionCode = appConfig.getProperty(AppConfig.APP_VERSION_CODE).toInt()
        versionName = appConfig.getProperty(AppConfig.APP_VERSION_NAME)
        minSdk = 24
        targetSdk = 33

        val resourceValues = listOf(
            ResourceValue("string", "app_name", appConfig.getProperty(AppConfig.APP_NAME))
        )
        resourceValues.forEach { resourceValue ->
            resValue(resourceValue.type, resourceValue.name, resourceValue.value)
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file(signingConfig.getProperty(SigningConfig.KEYSTORE_FILE))
            storePassword = signingConfig.getProperty(SigningConfig.KEYSTORE_PASSWORD)
            keyAlias = signingConfig.getProperty(SigningConfig.KEY_ALIAS)
            keyPassword = signingConfig.getProperty(SigningConfig.KEY_PASSWORD)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs["release"]
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    Libraries.implementations.forEach(::implementation)
    Libraries.testImplementations.forEach(::testImplementation)
    Libraries.androidTestImplementations.forEach(::androidTestImplementation)
    Libraries.debugImplementation.forEach(::debugImplementation)
    // platform bom
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
}