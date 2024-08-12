package io.nosyntax.foundation.domain.model.app_config

data class WebViewSettings(
    val javaScriptEnabled: Boolean,
    val cacheEnabled: Boolean,
    val geolocationEnabled: Boolean,
    val allowFileUploads: Boolean,
    val allowCameraAccess: Boolean
)