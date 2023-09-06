object Libraries {
    val plugins = listOf(
        "kotlin-android",
        "kotlin-kapt",
        "com.android.application",
        "dagger.hilt.android.plugin",
        "com.google.gms.google-services"
    )
    val implementations = listOf(
        "androidx.core:core-ktx:${Versions.core}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRunTime}",
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleRunTime}",
        "androidx.activity:activity-compose:${Versions.activityCompose}",
        "androidx.compose.material:material:${Versions.material}",
        "androidx.compose.ui:ui",
        "androidx.compose.ui:ui-graphics",
        "androidx.compose.ui:ui-tooling-preview",
        "androidx.navigation:navigation-compose:${Versions.navigation}",
        "androidx.compose.ui:ui-text-google-fonts:${Versions.googleFonts}",
        "androidx.compose.material3:material3",
        "io.coil-kt:coil-compose:${Versions.coilCompose}",
        "com.google.dagger:hilt-android:${Versions.daggerHilt}",
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.google.code.gson:gson:${Versions.gson}",
        "com.onesignal:OneSignal:${Versions.oneSignal}",
        "androidx.core:core-splashscreen:${Versions.splashScreen}",
    )
    val kapts = listOf(
        "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    )
    val testImplementations = listOf(
        "junit:junit:${Versions.junit}"
    )
    val androidTestImplementations = listOf(
        "androidx.test.ext:junit:${Versions.extJunit}",
        "androidx.test.espresso:espresso-core:${Versions.espressoCore}",
        "androidx.compose.ui:ui-test-junit4"
    )
    val debugImplementation = listOf(
        "androidx.compose.ui:ui-tooling",
        "androidx.compose.ui:ui-test-manifest"
    )
}

