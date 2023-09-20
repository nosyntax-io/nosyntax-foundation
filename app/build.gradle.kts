plugins {
    Libraries.plugins.forEach { id(it) }
}

val config = Properties().load(rootProject.file("local.properties"))
val signingConfig = Properties().load(rootProject.file("signing.properties"))

android {
    namespace = "app.mynta.template.android"
    compileSdk = 34

    defaultConfig {
        applicationId = config.getProperty(AppConfig.APP_ID)
        versionCode = config.getProperty(AppConfig.APP_VERSION_NUMBER).toInt()
        versionName = config.getProperty(AppConfig.APP_VERSION_NAME)
        minSdk = 24
        targetSdk = 34

        val resourceValues = listOf(
            ResourceValue("string", "app_name", config.getProperty(AppConfig.APP_NAME))
        )
        resourceValues.forEach { resourceValue ->
            resValue(resourceValue.type, resourceValue.name, resourceValue.value)
        }
        buildConfigField("String", "ACCESS_TOKEN", "\"${config.getProperty(AppConfig.APP_ACCESS_TOKEN)}\"")
        buildConfigField("String", "ONE_SIGNAL_APP_ID", "\"${config.getProperty(AppConfig.APP_ONESIGNAL_ID)}\"")

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
            if (config.getProperty(BuildConfig.BUILD_ENVIRONMENT) == "production") {
                isMinifyEnabled = true
                isShrinkResources = true
            }
            signingConfig = signingConfigs["release"]
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("regular") {
            dimension = "version"
        }
        create("monetize") {
            dimension = "version"
        }
    }
}

val monetizeImplementation by configurations

dependencies {
    Libraries.implementations.forEach(::implementation)
    Libraries.ksps.forEach(::ksp)
    Libraries.testImplementations.forEach(::testImplementation)
    Libraries.androidTestImplementations.forEach(::androidTestImplementation)
    Libraries.debugImplementation.forEach(::debugImplementation)
    // platform bom
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    // monetize
    monetizeImplementation("com.google.android.gms:play-services-ads:22.4.0")
}