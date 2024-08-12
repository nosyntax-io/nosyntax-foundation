package io.nosyntax.foundation.domain.model.app_config

data class AppConfig(
    val id: String,
    val name: String,
    val version: String,
    val description: String,
    val settings: Settings,
    val theme: Theme,
    val components: Components,
    val webViewSettings: WebViewSettings,
    val monetization: Monetization
)