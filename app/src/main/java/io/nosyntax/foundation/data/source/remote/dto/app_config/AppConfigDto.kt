package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("app")
    val app: App,
) {

    data class App(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("category")
        val category: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("theme")
        val theme: ThemeConfigDto,
        @SerializedName("components")
        val components: ComponentsConfigDto,
        @SerializedName("configuration")
        val configuration: ConfigurationDto,
        @SerializedName("navigation")
    val navigation: NavigationConfigDto
    )
}