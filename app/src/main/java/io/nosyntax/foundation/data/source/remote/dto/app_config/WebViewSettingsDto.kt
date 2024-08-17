package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class WebViewSettingsDto(
    @SerializedName("javascript_enabled")
    val javaScriptEnabled: Boolean,
    @SerializedName("cache_enabled")
    val cacheEnabled: Boolean,
    @SerializedName("geolocation_enabled")
    val geolocationEnabled: Boolean,
    @SerializedName("allow_file_uploads")
    val allowFileUploads: Boolean,
    @SerializedName("allow_camera_access")
    val allowCameraAccess: Boolean
)