import tasks.SetPermissionsTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)
}

val appConfig = AppConfig(rootProject.file("app-config.yml").toPath())

android {
    namespace = "io.nosyntax.foundation"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = appConfig.get("app.id")
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = appConfig.get("app.build_number")
        versionName = appConfig.get("app.version")

        resValue("string", "app_name", appConfig.get("app.name"))

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
            storeFile = rootProject.file(signingConfig.getProperty("signing.store_file"))
            storePassword = signingConfig.getProperty("signing.store_password")
            keyAlias = signingConfig.getProperty("signing.key_alias")
            keyPassword = signingConfig.getProperty("signing.key_password")
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
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.text.google.fonts)
    implementation(libs.navigation.compose)
    implementation(libs.documentfile)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)
    implementation(platform(libs.firebase.bom))
    implementation(libs.onesignal)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    "monetizeImplementation"(libs.play.services.ads)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.test.manifest)
    ksp(libs.hilt.compiler)
}

tasks.register<SetPermissionsTask>("setPermissions") {
    this.config = appConfig
}