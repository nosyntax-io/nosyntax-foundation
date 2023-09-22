package app.mynta.template.android.core.utility.monetize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import app.mynta.template.android.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(
    modifier: Modifier = Modifier,
    adId: String = BuildConfig.ADMOB_BANNER_ID
) {
    val configuration = LocalConfiguration.current

    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context, configuration.screenWidthDp
                    )
                )
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}