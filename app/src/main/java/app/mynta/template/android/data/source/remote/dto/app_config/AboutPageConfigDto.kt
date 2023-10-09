package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AboutPageConfigDto(
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("connect_items")
    val connectItems: List<ConnectItem>) {

    data class ConnectItem(
        @SerializedName("label")
        val label: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("url")
        val url: String
    )
}