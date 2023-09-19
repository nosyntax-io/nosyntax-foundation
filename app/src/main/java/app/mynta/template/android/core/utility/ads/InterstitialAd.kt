package app.mynta.template.android.core.utility.ads

import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAd(private val activity: ComponentActivity, private val adUnitId: String): LifecycleObserver {
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest = AdRequest.Builder().build()

    init {
        activity.lifecycle.addObserver(this)
    }

    fun loadInterstitialAd() {
        InterstitialAd.load(activity.applicationContext, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
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
        }
    }

    fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }
}