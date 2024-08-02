package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class SettingsDto(
    @SerializedName("entry_page")
    val entryPage: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("privacy_policy")
    val privacyPolicy: String,
    @SerializedName("terms_of_service")
    val termsOfService: String
)