package app.mynta.template.android.core.utility.monetize

import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import app.mynta.template.android.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAd(private val activity: ComponentActivity): LifecycleObserver {
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest = AdRequest.Builder().build()

    init {
        activity.lifecycle.addObserver(this)
    }

    fun loadInterstitialAd() {
        InterstitialAd.load(activity.applicationContext, BuildConfig.ADMOB_INTERSTITIAL_AD_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mInterstitialAd = interstitialAd
            }
        })

    }

    fun showInterstitialAd(onAdDismissed: () -> Unit) {
        mInterstitialAd?.let { interstitialAd ->
            interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitialAd()
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