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
                val themeColors: ThemeColors,
                @SerializedName("typography")
                val typography: Typography,
                @SerializedName("app_bar")
                val appBar: AppBar) {

                data class ThemeColors(
                    @SerializedName("primary")
                    val primary: String,
                    @SerializedName("secondary")
                    val secondary: String,
                    @SerializedName("highlight")
                    val highlight: String)

                data class Typography(
                    @SerializedName("heading_typeface")
                    val headingTypeface: String,
                    @SerializedName("body_typeface")
                    val bodyTypeface: String
                )

                data class AppBar(
                    @SerializedName("display")
                    val display: Boolean,
                    @SerializedName("background")
                    val background: String,
                    @SerializedName("display_title")
                    val displayTitle: Boolean
                )
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