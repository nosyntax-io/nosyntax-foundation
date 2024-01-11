package io.nosyntax.foundation.domain.model.app_config

data class AppConfig(
    val app: App,
    val configuration: Configuration
) {

    data class App(
        val id: String,
        val name: String,
        val category: String,
        val description: String
    )

    data class Configuration(
        val theme: ThemeConfig,
        val components: ComponentsConfig,
        val monetization: MonetizationConfig,
        val modules: ModulesConfig,
        val navigation: NavigationConfig
    )
}