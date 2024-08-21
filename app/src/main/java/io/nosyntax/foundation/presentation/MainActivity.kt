package io.nosyntax.foundation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.nosyntax.foundation.core.util.Connectivity
import io.nosyntax.foundation.core.util.collectLatestOnLifecycleStarted
import io.nosyntax.foundation.core.util.monetize.InterstitialAd
import io.nosyntax.foundation.presentation.theme.FoundationTheme
import dagger.hilt.android.AndroidEntryPoint
import io.nosyntax.foundation.presentation.component.NoConnectionView
import io.nosyntax.foundation.core.util.Utilities.getSerializable
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.presentation.screen.main.MainScreen
import io.nosyntax.foundation.presentation.theme.ThemeProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var themeProvider: ThemeProvider

    private var interstitialAd: InterstitialAd? = null
    private var isAdsEnabled = false
    private var isInterstitialAdEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeProvider = ThemeProvider()

        val deeplink = getSerializable(this, "deeplink", Deeplink::class.java)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isInitialized.value
            }
        }

        collectLatestOnLifecycleStarted(mainViewModel.appConfig) { state ->
            state.response?.let { response ->
                val theme = response.theme
                val components = response.components

                setContent {
                    val (colorScheme, typography) = themeProvider.getTheme(theme)
                    val statusBarColor = "neutral"
                    val isDarkTheme = if (theme.darkMode) isSystemInDarkTheme() else false

                    FoundationTheme(
                        colorScheme = colorScheme,
                        typography = typography,
                        statusBarColor = statusBarColor,
                        darkTheme = isDarkTheme,
                        content = { MainScreen(deeplink = deeplink) }
                    )
                }

                val ads = response.monetization.ads
                if (ads.enabled && ads.interstitialDisplay) {
                    interstitialAd = InterstitialAd(this@MainActivity).load()
                }
                isAdsEnabled = ads.enabled && ads.interstitialDisplay
                isInterstitialAdEnabled = ads.interstitialDisplay
            } ?: run {
                setContent {
                    FoundationTheme {
                        NoConnectionView(onRetry = {
                            if (Connectivity.getInstance().isOnline()) {
                                mainViewModel.getAppConfig()
                            }
                        })
                    }
                }
            }
        }
    }

    fun showInterstitial(onAdDismissed: () -> Unit = {}) {
        if (isAdsEnabled && isInterstitialAdEnabled) {
            interstitialAd?.show(onAdDismissed = onAdDismissed)
        } else {
            onAdDismissed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd?.release()
    }
}