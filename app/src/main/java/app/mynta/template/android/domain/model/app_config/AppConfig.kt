package app.mynta.template.android.domain.model.app_config

data class AppConfig(
    val appId: String,
    val appearance: AppearanceConfig,
    val navigation: NavigationConfig
)