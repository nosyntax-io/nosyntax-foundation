package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("settings")
    val settings: SettingsDto,
    @SerializedName("theme")
    val theme: ThemeDto,
    @SerializedName("components")
    val components: ComponentsDto,
    @SerializedName("monetization_options")
    val monetization: MonetizationDto
)