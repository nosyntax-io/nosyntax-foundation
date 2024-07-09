import org.yaml.snakeyaml.Yaml
import java.nio.file.Files

plugins {
    Dependencies.plugins.forEach { id(it) }
}

android {
    namespace = "io.nosyntax.foundation"
    compileSdk = 34

    defaultConfig {
        applicationId = getConfig("app.id")
        versionName = getConfig("app.version")
        versionCode = getConfig("app.build_number")
        minSdk = 24
        targetSdk = 34

        val resourceValues = listOf(
            ResourceValue("string", "app_name", getConfig("app.name"))
        )
        resourceValues.forEach { resourceValue ->
            resValue(resourceValue.type, resourceValue.name, resourceValue.value)
        }
        buildConfigField("String", "SERVER_AUTH_TOKEN", "\"${getConfig("server.auth_token") as String}\"")
        buildConfigField("String", "SERVER_ACCESS_TOKEN", "\"${getConfig("server.access_token") as String}\"")
        buildConfigField("String", "APP_REMOTE_CONFIG", "\"enabled\"")
        buildConfigField("String", "ONESIGNAL_APP_ID", "\"${getConfig("integrations.onesignal.app_id") as String}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${getConfig("integrations.admob.banner_id") as String}\"")
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${getConfig("integrations.admob.interstitial_id") as String}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val signingConfig = Properties().load(rootProject.file("signing.properties"))
            storeFile = rootProject.file(signingConfig.getProperty(SigningConfig.KEYSTORE_FILE))
            storePassword = signingConfig.getProperty(SigningConfig.KEYSTORE_PASSWORD)
            keyAlias = signingConfig.getProperty(SigningConfig.KEY_ALIAS)
            keyPassword = signingConfig.getProperty(SigningConfig.KEY_PASSWORD)
        }
    }

    flavorDimensions += "default"
    productFlavors {
        create("regular") {
            isDefault = true
            dimension = "default"
        }
        create("monetize") {
            manifestPlaceholders += mapOf("admob_app_id" to getConfig("integrations.admob.app_id") as String)
            dimension = "default"
        }
    }

    buildTypes {
        release {
            if (getConfig("build.environment") as String == "production") {
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

inline fun <reified T> getConfig(path: String, file: String = "app-config.yml"): T {
    val configContent = Files.readString(rootProject.file(file).toPath())
    val config = Yaml().load<Map<String, Any>>(configContent)

    val value = path.split(".").fold(config as Any?) { current, key ->
        (current as? Map<*, *>)?.get(key)
    }

    return (value as? T) ?: when (T::class) {
        String::class -> "" as T
        Int::class -> 0 as T
        Boolean::class -> false as T
        else -> throw IllegalArgumentException("Unsupported type")
    }
}