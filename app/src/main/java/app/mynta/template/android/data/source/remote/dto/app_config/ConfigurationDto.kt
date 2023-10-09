package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("appearance")
    val appearance: AppearanceConfigDto,
    @SerializedName("components")
    val components: ComponentsConfigDto,
    @SerializedName("monetization_options")
    val monetization: MonetizationConfigDto,
    @SerializedName("modules")
    val modules: ModulesConfigDto,
    @SerializedName("about_page")
    val aboutPage: AboutPageConfigDto
)