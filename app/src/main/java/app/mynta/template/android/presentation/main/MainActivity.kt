package app.mynta.template.android.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.mynta.template.android.core.components.NoConnectionComponent
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Coroutines.collectLatestOnLifecycleStarted
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.ui.theme.MyntaTemplateTheme
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
                MyntaTemplateTheme {
                    when {
                        state.response != null -> {
                            HomeScreen()
                        }
                        state.error != null -> {
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