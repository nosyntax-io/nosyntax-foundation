package io.nosyntax.foundation.domain.model.app_config

data class ComponentsConfig(
    val appBar: AppBarConfig,
    val sideMenu: SideMenuConfig,
    val bottomBar: BottomBarConfig,
    val loadingIndicator: LoadingIndicatorConfig
)