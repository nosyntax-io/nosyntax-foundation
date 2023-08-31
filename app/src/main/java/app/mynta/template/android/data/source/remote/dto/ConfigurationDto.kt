package app.mynta.template.android.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("app")
    val app: App) {

    data class App(
        @SerializedName("app_id")
        val appId: String,
        @SerializedName("configuration")
        val configuration: Configuration) {

        data class Configuration(
            @SerializedName("appearance")
            val appearance: Appearance,
            @SerializedName("navigation")
            val navigation: Navigation) {

            data class Appearance(
                @SerializedName("theme_colors")
                val themeColors: ThemeColors) {

                data class ThemeColors(
                    @SerializedName("primary")
                    val primary: String,
                    @SerializedName("secondary")
                    val secondary: String,
                    @SerializedName("highlight")
                    val highlight: String)
            }

            data class Navigation(
                @SerializedName("default")
                val default: String,
                @SerializedName("items")
                val items: List<NavigationItem>) {

                data class NavigationItem(
                    @SerializedName("id")
                    val id: String,
                    @SerializedName("type")
                    val type: String,
                    @SerializedName("label")
                    val label: String,
                    @SerializedName("icon")
                    val icon: String
                )
            }
        }
    }
}