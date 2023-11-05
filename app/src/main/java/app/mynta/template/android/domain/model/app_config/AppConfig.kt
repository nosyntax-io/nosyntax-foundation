package app.mynta.template.android.domain.model.app_config

data class AppConfig(
    val id: String,
    val name: String,
    val description: String,
    val theme: ThemeConfig,
    val components: ComponentsConfig,
    val monetization: MonetizationConfig,
    val modules: ModulesConfig
)