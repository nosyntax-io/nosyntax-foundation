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
import io.nosyntax.foundation.core.utility.Connectivity
import io.nosyntax.foundation.core.utility.collectLatestOnLifecycleStarted
import io.nosyntax.foundation.core.utility.monetize.InterstitialAd
import io.nosyntax.foundation.ui.theme.DynamicTheme
import io.nosyntax.foundation.ui.theme.DynamicColorScheme
import io.nosyntax.foundation.ui.theme.DynamicTypography
import io.nosyntax.foundation.ui.theme.googleFontProvider
import dagger.hilt.android.AndroidEntryPoint
import io.nosyntax.foundation.core.component.NoConnectionView
import io.nosyntax.foundation.core.utility.Utilities.getSerializable
import io.nosyntax.foundation.domain.model.Deeplink

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private var interstitialAd: InterstitialAd? = null
    private var isAdsEnabled = false
    private var isInterstitialAdEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                val (colorScheme, typography) = theme.run {
                    colorScheme to typography
                }

                val dynamicColorScheme = DynamicColorScheme(
                    primary = Color(parseColor(colorScheme.primary)),
                    onPrimary = Color(parseColor(colorScheme.onPrimary)),
                    secondary = Color(parseColor(colorScheme.secondary)),
                    onSecondary = Color(parseColor(colorScheme.onSecondary)),
                    backgroundLight = Color(parseColor(colorScheme.backgroundLight)),
                    onBackgroundLight = Color(parseColor(colorScheme.onBackgroundLight)),
                    surfaceLight = Color(parseColor(colorScheme.surfaceLight)),
                    onSurfaceLight = Color(parseColor(colorScheme.onSurfaceLight)),
                    outlineLight = Color(parseColor(colorScheme.outlineLight)),
                    outlineVariantLight = Color(parseColor(colorScheme.outlineVariantLight)),
                    backgroundDark = Color(parseColor(colorScheme.backgroundDark)),
                    onBackgroundDark = Color(parseColor(colorScheme.onBackgroundDark)),
                    surfaceDark = Color(parseColor(colorScheme.surfaceDark)),
                    onSurfaceDark = Color(parseColor(colorScheme.onSurfaceDark)),
                    outlineDark = Color(parseColor(colorScheme.outlineDark)),
                    outlineVariantDark = Color(parseColor(colorScheme.outlineVariantDark))
                )

                val dynamicTypography = DynamicTypography(
                    primaryFontFamily = FontFamily(Font(GoogleFont(typography.primaryFontFamily), googleFontProvider)),
                    secondaryFontFamily = FontFamily(Font(GoogleFont(typography.secondaryFontFamily), googleFontProvider))
                )

                setContent {
                    DynamicTheme(
                        dynamicColorScheme = dynamicColorScheme,
                        dynamicTypography = dynamicTypography,
                        statusBarColor = components.appBar.background,
                        darkTheme = if (theme.darkMode) isSystemInDarkTheme() else false,
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
                    DynamicTheme {
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