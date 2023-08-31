package app.mynta.template.android.presentation.main

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.mynta.template.android.core.components.NoConnectionComponent
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Coroutines.collectLatestOnLifecycleStarted
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.ui.theme.DynamicTheme
import app.mynta.template.android.ui.theme.DynamicThemeColors
import app.mynta.template.android.ui.theme.DynamicTypography
import app.mynta.template.android.ui.theme.googleFontProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isInitialized.value
            }
        }

        collectLatestOnLifecycleStarted(mainViewModel.configuration) { state ->
            setContent {
                when {
                    state.response != null && state.error == null -> {
                        val appearance = state.response.appearance
                        val themeColors = appearance.themeColors
                        val typography = appearance.typography

                        val dynamicThemeColors = DynamicThemeColors(
                            primaryColor = Color(parseColor(themeColors.primary)),
                            secondaryColor = Color(parseColor(themeColors.secondary)),
                            primaryContainer = Color(parseColor(themeColors.highlight))
                        )
                        val dynamicTypography = DynamicTypography(
                            headingTypeface = FontFamily(Font(GoogleFont(typography.headingTypeface), googleFontProvider)),
                            bodyTypeface = FontFamily(Font(GoogleFont(typography.bodyTypeface), googleFontProvider))
                        )
                        DynamicTheme(dynamicThemeColors, dynamicTypography) {
                            HomeScreen()
                        }
                    }
                    state.error != null -> {
                        DynamicTheme {
                            NoConnectionComponent(onRetry = {
                                if (Connectivity.getInstance().isOnline()) {
                                    mainViewModel.requestConfiguration()
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}