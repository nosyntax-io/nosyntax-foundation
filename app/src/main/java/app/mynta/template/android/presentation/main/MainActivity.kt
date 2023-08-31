package app.mynta.template.android.presentation.main

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.mynta.template.android.core.components.NoConnectionComponent
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Coroutines.collectLatestOnLifecycleStarted
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.ui.theme.DynamicTheme
import app.mynta.template.android.ui.theme.DynamicThemeColors
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
                        val configuration = state.response
                        val themeColors = configuration.appearance.themeColors
                        DynamicTheme(dynamicColors = DynamicThemeColors(
                            primaryColor = Color(parseColor(themeColors.primary)),
                            secondaryColor = Color(parseColor(themeColors.secondary)),
                            primaryContainer = Color(parseColor(themeColors.highlight))
                        )) {
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