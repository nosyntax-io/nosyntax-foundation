package app.mynta.template.android.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.mynta.template.android.core.utility.Coroutines.collectLatestOnLifecycleStarted
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.ui.theme.MyntaTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSplashScreen()
        setContent {
            MyntaTemplateTheme {
                HomeScreen()
            }
        }
    }

    private fun setSplashScreen() {
        installSplashScreen().apply {
            setOnExitAnimationListener { splashScreen ->
                mainViewModel.requestConfiguration()
                collectLatestOnLifecycleStarted(mainViewModel.configuration) { state ->
                    if (state.response != null) {
                        splashScreen.remove()
                    }
                }
            }
        }
    }
}