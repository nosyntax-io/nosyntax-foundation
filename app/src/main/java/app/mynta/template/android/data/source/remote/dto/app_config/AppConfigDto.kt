package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("app")
    val app: App) {

    data class App(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("configuration")
        val configuration: ConfigurationDto
    )
}