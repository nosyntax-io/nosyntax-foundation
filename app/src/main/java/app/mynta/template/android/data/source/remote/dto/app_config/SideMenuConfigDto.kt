package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class SideMenuConfigDto(
    @SerializedName("display")
    val display: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("header")
    val header: Header) {

    data class Header(
        @SerializedName("display")
        val display: Boolean,
        @SerializedName("image")
        val image: String
    )
}