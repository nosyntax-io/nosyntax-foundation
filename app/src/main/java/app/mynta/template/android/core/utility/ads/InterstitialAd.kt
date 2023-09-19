package app.mynta.template.android.core.utility.ads

import android.widget.Toast
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
    private var adLoaded = false

    init {
        activity.lifecycle.addObserver(this)
    }

    fun loadInterstitialAd() {
        if (!adLoaded) {
            InterstitialAd.load(activity.applicationContext, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    mInterstitialAd = null
                    adLoaded = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    mInterstitialAd = interstitialAd
                    adLoaded = true
                }
            })
        }
    }

    fun showInterstitialAd(onAdDismissed: () -> Unit) {
        if (adLoaded) {
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
        } else {
            Toast.makeText(activity.applicationContext, "Ad is not loaded", Toast.LENGTH_LONG).show()
        }
    }

    fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }
}