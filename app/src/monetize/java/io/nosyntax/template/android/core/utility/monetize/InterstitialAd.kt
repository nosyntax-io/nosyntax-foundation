package io.nosyntax.template.android.core.utility.monetize

import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.nosyntax.template.android.BuildConfig

class InterstitialAd(private val activity: ComponentActivity): LifecycleObserver {
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest = AdRequest.Builder().build()

    init {
        activity.lifecycle.addObserver(this)
    }

    fun load(): io.nosyntax.template.android.core.utility.monetize.InterstitialAd {
        InterstitialAd.load(activity.applicationContext, BuildConfig.ADMOB_INTERSTITIAL_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mInterstitialAd = interstitialAd
            }
        })
        return this
    }

    fun show(onAdDismissed: () -> Unit) {
        mInterstitialAd?.let { interstitialAd ->
            interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    load()
                    onAdDismissed()
                }
            }
            interstitialAd.show(activity)
        } ?: run {
            onAdDismissed()
        }
    }

    fun release() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }
}