package io.nosyntax.foundation.core.utility.monetize

import androidx.activity.ComponentActivity

class InterstitialAd(private val activity: ComponentActivity, private val adUnitId: String = "") {
    fun load(): InterstitialAd {
        return this
    }

    fun show(onAdDismissed: () -> Unit) {
        onAdDismissed()
    }

    fun release() {

    }
}