package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("app")
    val app: App,
) {

    data class App(
        @SerializedName("details")
        val details: DetailsDto,
        @SerializedName("theme")
        val theme: ThemeConfigDto,
        @SerializedName("components")
        val components: ComponentsConfigDto,
        @SerializedName("configuration")
        val configuration: ConfigurationDto
    )
}