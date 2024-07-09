import org.gradle.kotlin.dsl.DependencyHandlerScope

object Versions {
    const val core = "1.12.0"
    const val coreSplashScreen = "1.0.1"
    const val composeBom = "2024.03.00"
    const val navigation = "2.7.7"
    const val hilt = "2.48"
    const val hiltNavigation = "1.2.0"
    const val retrofit = "2.10.0"
    const val gson = "2.10.1"
    const val firebaseBom = "32.8.0"
    const val coil = "2.6.0"
    const val oneSignal = "[4.0.0, 4.99.99]"
    const val lottie = "6.4.0"
    const val playServicesAds = "23.0.0"
    const val junit = "4.13.2"
    const val extJunit = "1.1.5"
    const val espressoCore = "3.5.1"
    const val snakeYaml = "1.29"
}

object Dependencies {
    val plugins = listOf(
        "com.android.application",
        "org.jetbrains.kotlin.android",
        "com.google.devtools.ksp",
        "dagger.hilt.android.plugin",
        "com.google.gms.google-services"
    )
    val core = listOf(
        "androidx.core:core-ktx:${Versions.core}",
        "androidx.core:core-splashscreen:${Versions.coreSplashScreen}",
        "platform:androidx.compose:compose-bom:${Versions.composeBom}",
        "androidx.compose.material3:material3",
        "androidx.compose.ui:ui",
        "androidx.compose.ui:ui-graphics",
        "androidx.compose.ui:ui-tooling-preview",
        "androidx.compose.ui:ui-text-google-fonts",
        "androidx.navigation:navigation-compose:${Versions.navigation}",
        "com.google.dagger:hilt-android:${Versions.hilt}",
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.google.code.gson:gson:${Versions.gson}",
        "platform:com.google.firebase:firebase-bom:${Versions.firebaseBom}",
        "io.coil-kt:coil-compose:${Versions.coil}",
        "com.onesignal:OneSignal:${Versions.oneSignal}",
        "com.airbnb.android:lottie-compose:${Versions.lottie}",
        "org.yaml:snakeyaml:${Versions.snakeYaml}"
    )
    val monetize = listOf(
        "com.google.android.gms:play-services-ads:${Versions.playServicesAds}"
    )
    val ksp = listOf(
        "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    )
    val test = listOf(
        "junit:junit:${Versions.junit}"
    )
    val androidTest = listOf(
        "platform:androidx.compose:compose-bom:${Versions.composeBom}",
        "androidx.test.ext:junit:${Versions.extJunit}",
        "androidx.test.espresso:espresso-core:${Versions.espressoCore}",
        "androidx.compose.ui:ui-test-junit4"
    )
    val debug = listOf(
        "androidx.compose.ui:ui-tooling",
        "androidx.compose.ui:ui-test-manifest"
    )
}

fun DependencyHandlerScope.implementDependencies(configuration: String, dependencies: List<String>) {
    dependencies.map { dependency ->
        if (dependency.startsWith("platform:")) {
            val platformNotation = dependency.removePrefix("platform:")
            platform(platformNotation)
        } else {
            dependency
        }
    }.forEach { resolvedDependency ->
        this.add(configuration, resolvedDependency)
    }
}