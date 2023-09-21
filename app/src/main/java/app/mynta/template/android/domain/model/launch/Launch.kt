package app.mynta.template.android.domain.model.launch

import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.model.envato.EnvatoAuthorSale

data class Launch(
    val appConfig: AppConfig,
    val authorSale: EnvatoAuthorSale
)