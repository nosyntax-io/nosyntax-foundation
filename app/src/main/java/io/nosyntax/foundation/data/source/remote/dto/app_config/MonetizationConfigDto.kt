package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class MonetizationConfigDto(
    @SerializedName("ads")
    val ads: Ads
) {
    data class Ads(
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("banner_display")
        val bannerDisplay: Boolean,
        @SerializedName("interstitial_display")
        val interstitialDisplay: Boolean
    )
}