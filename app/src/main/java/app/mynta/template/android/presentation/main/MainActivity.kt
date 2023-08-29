package app.mynta.template.android.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import app.mynta.template.android.domain.model.MenuItem
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.ui.theme.MyntaTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    // demonstrate date coming from server
    private val navigationItems = listOf(
        MenuItem("web1", "Web Link 1", "web1", Icons.Default.KeyboardArrowRight),
        MenuItem("web2", "Web Link 2", "web2", Icons.Default.KeyboardArrowRight),
        MenuItem("web3", "Web Link 3", "web3", Icons.Default.KeyboardArrowRight),
        MenuItem("web4", "Web Link 4", "web4", Icons.Default.KeyboardArrowRight),
        MenuItem("web5", "Web Link 5", "web5", Icons.Default.KeyboardArrowRight)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isInitialized.value
            }
        }

        setContent {
            MyntaTemplateTheme {
                HomeScreen(navigationItems)
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}