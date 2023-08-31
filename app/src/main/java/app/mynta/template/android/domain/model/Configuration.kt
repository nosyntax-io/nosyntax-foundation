package app.mynta.template.android.domain.model

data class Configuration(
    val appId: String,
    val appearance: Appearance,
    val navigation: Navigation) {

    data class Appearance(
        val themeColors: ThemeColors) {

        data class ThemeColors(
            val primary: String,
            val secondary: String,
            val highlight: String)
    }

    data class Navigation(
        val default: String,
        val items: List<NavigationItem>)
}