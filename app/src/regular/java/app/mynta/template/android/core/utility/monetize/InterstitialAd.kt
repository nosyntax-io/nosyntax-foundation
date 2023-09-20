package app.mynta.template.android.core.utility.monetize

import androidx.activity.ComponentActivity

class InterstitialAd(private val activity: ComponentActivity, private val adUnitId: String = "") {
    fun loadInterstitialAd() {

    }

    fun showInterstitialAd(onAdDismissed: () -> Unit) {
        onAdDismissed()
    }

    fun removeInterstitial() {

    }
}