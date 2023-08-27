object Libraries {
    val plugins = listOf(
        "kotlin-android",
        "kotlin-kapt",
        "com.android.application",
        "dagger.hilt.android.plugin",
    )
    val implementations = listOf(
        "androidx.core:core-ktx:1.9.0",
        "androidx.core:core-ktx:1.9.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1",
        "androidx.activity:activity-compose:1.7.2",
        "androidx.compose.ui:ui",
        "androidx.compose.ui:ui-graphics",
        "androidx.compose.ui:ui-tooling-preview",
        "androidx.compose.material3:material3",
        "com.google.dagger:hilt-android:2.44",
    )
    val kapts = listOf(
        "com.google.dagger:hilt-android-compiler:2.44"
    )
    val testImplementations = listOf(
        "junit:junit:4.13.2"
    )
    val androidTestImplementations = listOf(
        "androidx.test.ext:junit:1.1.5",
        "androidx.test.espresso:espresso-core:3.5.1",
        "androidx.compose.ui:ui-test-junit4"
    )
    val debugImplementation = listOf(
        "androidx.compose.ui:ui-tooling",
        "androidx.compose.ui:ui-test-manifest"
    )
}

