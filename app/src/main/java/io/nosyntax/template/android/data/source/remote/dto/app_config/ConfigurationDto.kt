package io.nosyntax.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("theme")
    val theme: ThemeConfigDto,
    @SerializedName("components")
    val components: ComponentsConfigDto,
    @SerializedName("monetization_options")
    val monetization: MonetizationConfigDto,
    @SerializedName("modules")
    val modules: ModulesConfigDto,
    @SerializedName("navigation")
    val navigation: NavigationConfigDto
)