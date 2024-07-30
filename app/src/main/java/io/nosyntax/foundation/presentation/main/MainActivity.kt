package io.nosyntax.foundation.presentation.main

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.NoConnectionComponent
import io.nosyntax.foundation.core.utility.Connectivity
import io.nosyntax.foundation.core.utility.collectLatestOnLifecycleStarted
import io.nosyntax.foundation.core.utility.monetize.InterstitialAd
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.presentation.home.HomeScreen
import io.nosyntax.foundation.ui.theme.DynamicTheme
import io.nosyntax.foundation.ui.theme.DynamicColorScheme
import io.nosyntax.foundation.ui.theme.DynamicTypography
import io.nosyntax.foundation.ui.theme.googleFontProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private var interstitialAd: InterstitialAd? = null
    private var isAdsEnabled = false
    private var isInterstitialAdEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isInitialized.value
            }
        }

        intent.getStringExtra(Constants.DEEPLINK)?.let { deeplink ->
            mainViewModel.setDeeplink(
                Deeplink(
                destination = deeplink
            )
            )
        }

        collectLatestOnLifecycleStarted(mainViewModel.appConfig) { state ->
            when {
                state.response != null && state.error == null -> {
                    val theme = state.response.app.theme
                    val colorScheme = theme.colorScheme
                    val typography = theme.typography
                    val components = state.response.app.components

                    val dynamicColorScheme = DynamicColorScheme(
                        colorPrimary = Color(parseColor(colorScheme.primary)),
                        colorOnPrimary = Color(parseColor(colorScheme.onPrimary)),
                        colorSecondary = Color(parseColor(colorScheme.secondary)),
                        colorOnSecondary = Color(parseColor(colorScheme.onSecondary)),
                        colorBackgroundLight = Color(parseColor(colorScheme.backgroundLight)),
                        colorOnBackgroundLight = Color(parseColor(colorScheme.onBackgroundLight)),
                        colorSurfaceLight = Color(parseColor(colorScheme.surfaceLight)),
                        colorOnSurfaceLight = Color(parseColor(colorScheme.onSurfaceLight)),
                        colorBackgroundDark = Color(parseColor(colorScheme.backgroundDark)),
                        colorOnBackgroundDark = Color(parseColor(colorScheme.onBackgroundDark)),
                        colorSurfaceDark = Color(parseColor(colorScheme.surfaceDark)),
                        colorOnSurfaceDark = Color(parseColor(colorScheme.onSurfaceDark))
                    )
                    val dynamicTypography = DynamicTypography(
                        primaryFontFamily = FontFamily(Font(GoogleFont(typography.primaryFontFamily), googleFontProvider)),
                        secondaryFontFamily = FontFamily(Font(GoogleFont(typography.secondaryFontFamily), googleFontProvider))
                    )
                    val statusBarColor = components.appBar.background

                    setContent {
                        val darkTheme = if (theme.settings.darkMode) {
                            isSystemInDarkTheme()
                        } else {
                            false
                        }

                        DynamicTheme(
                            dynamicColorScheme = dynamicColorScheme,
                            dynamicTypography = dynamicTypography,
                            statusBarColor = statusBarColor,
                            darkTheme = darkTheme
                        ) {
                            HomeScreen()
                        }
                    }

                    val ads = state.response.app.configuration.monetization.ads
                    if (ads.enabled && ads.interstitialDisplay) {
                        interstitialAd = InterstitialAd(this@MainActivity).load()
                    }
                    isAdsEnabled = ads.enabled && ads.interstitialDisplay
                    isInterstitialAdEnabled = ads.interstitialDisplay
                }
                state.error != null -> {
                    setContent {
                        DynamicTheme {
                            NoConnectionComponent(onRetry = {
                                if (Connectivity.getInstance().isOnline()) {
                                    mainViewModel.requestAppConfig()
                                }
                            })
                        }
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