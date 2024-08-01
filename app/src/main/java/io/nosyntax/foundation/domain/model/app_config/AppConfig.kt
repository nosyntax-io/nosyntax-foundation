package io.nosyntax.foundation.domain.model.app_config

data class AppConfig(
    val app: App,
) {

    data class App(
        val id: String,
        val name: String,
        val category: String,
        val description: String,
        val theme: ThemeConfig,
        val components: ComponentsConfig,
        val configuration: Configuration
    )

    data class Configuration(
        val monetization: MonetizationConfig
    )
}