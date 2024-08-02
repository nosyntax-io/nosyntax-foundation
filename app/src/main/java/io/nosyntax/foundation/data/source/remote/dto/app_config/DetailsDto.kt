package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class DetailsDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("privacy_policy")
    val privacyPolicy: String
)