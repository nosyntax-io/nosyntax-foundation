package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("app")
    val app: App) {

    data class App(
        @SerializedName("app_id")
        val appId: String,
        @SerializedName("configuration")
        val configuration: ConfigurationDto
    )
}