package app.mynta.template.android.core.utility.monetize

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.ads.MobileAds

object MonetizeController {
    fun initialize(context: Context) {
        MobileAds.initialize(context)
    }

    @Composable
    fun InitializeInterstitial(context: Context) {
        val interstitialAd = remember {
            InterstitialAd(context as ComponentActivity, "")
        }
        interstitialAd.loadInterstitialAd()


    }


}