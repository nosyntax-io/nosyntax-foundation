package app.mynta.template.android.domain.model.app_config

data class MonetizationConfig(
    val ads: Ads) {

    data class Ads(
        val bannerDisplay: Boolean,
        val interstitialDisplay: Boolean
    )
}