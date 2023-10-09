package app.mynta.template.android.domain.model.app_config

data class AppConfig(
    val appId: String,
    val appearance: AppearanceConfig,
    val components: ComponentsConfig,
    val monetization: MonetizationConfig,
    val modules: ModulesConfig,
    val aboutPage: AboutPageConfig
)