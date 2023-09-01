package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("appearance")
    val appearance: AppearanceConfigDto,
    @SerializedName("navigation")
    val navigation: NavigationConfigDto,
    @SerializedName("about_page")
    val aboutPage: AboutPageConfigDto
)