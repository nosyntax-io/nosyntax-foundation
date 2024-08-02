package io.nosyntax.foundation.domain.model.app_config

data class Monetization(
    val ads: Ads
) {

    data class Ads(
        val enabled: Boolean,
        val bannerDisplay: Boolean,
        val interstitialDisplay: Boolean
    )
}