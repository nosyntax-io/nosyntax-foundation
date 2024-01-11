package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ComponentsConfigDto(
    @SerializedName("app_bar")
    val appBar: AppBarConfigDto,
    @SerializedName("side_menu")
    val sideMenu: SideMenuConfigDto,
    @SerializedName("bottom_bar")
    val bottomBar: BottomBarConfigDto,
    @SerializedName("loading_indicator")
    val loadingIndicator: LoadingIndicatorConfigDto
)