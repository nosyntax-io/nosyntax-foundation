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
        "androidx.activity:activity-compose:${Versions.activityCompose}",
        "androidx.compose.ui:ui",
        "androidx.compose.ui:ui-graphics",
        "androidx.compose.ui:ui-tooling-preview",
        "androidx.compose.material3:material3",
        "com.google.dagger:hilt-android:${Versions.daggerHilt}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.google.code.gson:gson:${Versions.gson}",
        "com.onesignal:OneSignal:${Versions.oneSignal}",
    )
    val kapts = listOf(
        "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    )
    val testImplementations = listOf(
        "junit:junit:${Versions.junit}"
    )
    val androidTestImplementations = listOf(
        "androidx.test.ext:junit:${Versions.junit}",
        "androidx.test.espresso:espresso-core:${Versions.espressoCore}",
        "androidx.compose.ui:ui-test-junit4"
    )
    val debugImplementation = listOf(
        "androidx.compose.ui:ui-tooling",
        "androidx.compose.ui:ui-test-manifest"
    )
}

