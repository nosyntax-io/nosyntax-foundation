package io.nosyntax.template.android.core.utility.monetize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import io.nosyntax.template.android.BuildConfig

@Composable
fun BannerAd(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    adId: String = BuildConfig.ADMOB_BANNER_ID
) {
    val configuration = LocalConfiguration.current

    if (enabled) {
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
}