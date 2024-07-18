plugins {
    Dependencies.plugins.forEach { id(it) }
}

val appConfig = AppConfig(rootProject.file("app-config.yml").toPath())

android {
    namespace = "io.nosyntax.foundation"
    compileSdk = 34

    defaultConfig {
        applicationId = appConfig.get("app.id")
        versionName = appConfig.get("app.version")
        versionCode = appConfig.get("app.build_number")
        minSdk = 24
        targetSdk = 34

        val resourceValues = listOf(
            ResourceValue("string", "app_name", appConfig.get("app.name"))
        )
        resourceValues.forEach { resourceValue ->
            resValue(resourceValue.type, resourceValue.name, resourceValue.value)
        }
        buildConfigField("String", "SERVER_AUTH_TOKEN", "\"${appConfig.get("server.auth_token") as String}\"")
        buildConfigField("String", "SERVER_ACCESS_TOKEN", "\"${appConfig.get("server.access_token") as String}\"")
        buildConfigField("String", "APP_REMOTE_CONFIG", "\"enabled\"")
        buildConfigField("String", "ONESIGNAL_APP_ID", "\"${appConfig.get("integrations.onesignal.app_id") as String}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${appConfig.get("integrations.admob.banner_id") as String}\"")
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${appConfig.get("integrations.admob.interstitial_id") as String}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val signingConfig = Properties().load(rootProject.file("signing.properties"))
            storeFile = rootProject.file(signingConfig.getProperty("signing.keystore_file"))
            storePassword = signingConfig.getProperty("signing.keystore_password")
            keyAlias = signingConfig.getProperty("signing.key_alias")
            keyPassword = signingConfig.getProperty("signing.key_password")
        }
    }

    flavorDimensions += "default"
    productFlavors {
        create("regular") {
            isDefault = true
            dimension = "default"
        }
        create("monetize") {
            manifestPlaceholders += mapOf("admob_app_id" to appConfig.get("integrations.admob.app_id") as String)
            dimension = "default"
        }
    }

    buildTypes {
        release {
            if (appConfig.get("build.environment") as String == "production") {
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
}

dependencies {
    implementDependencies("implementation", Dependencies.core)
    implementDependencies("monetizeImplementation", Dependencies.monetize)
    implementDependencies("testImplementation", Dependencies.test)
    implementDependencies("androidTestImplementation", Dependencies.androidTest)
    implementDependencies("debugImplementation", Dependencies.debug)
    // kotlin symbol processing (KSP)
    Dependencies.ksp.forEach(::ksp)
}

tasks.register("setPermissions") {
    doLast {
        val permissions = appConfig.getPermissions()
        val manifestFile = file("src/main/AndroidManifest.xml")

        if (!manifestFile.exists() || !manifestFile.canRead() || !manifestFile.canWrite()) {
            throw IllegalStateException("Manifest file is not accessible")
        }

        val permissionDeclarations = permissions.joinToString("\n") { "    <uses-permission android:name=\"$it\" />" }
        val updatedManifestContent = manifestFile.readText().replace("<application", "$permissionDeclarations\n\n    <application")
        manifestFile.writeText(updatedManifestContent)
    }
}