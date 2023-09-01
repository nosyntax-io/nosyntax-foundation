package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AboutPageConfigDto(
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("social_links")
    val socialLinks: List<SocialLink>) {

    data class SocialLink(
        @SerializedName("id")
        val id: String,
        @SerializedName("label")
        val label: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("url")
        val url: String
    )
}