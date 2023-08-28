package app.mynta.template.android.core.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import app.mynta.template.android.core.utility.SystemUIController
import app.mynta.template.android.core.utility.Utilities.findActivity

enum class SystemUIState {
    SYSTEM_UI_VISIBLE,
    SYSTEM_UI_HIDDEN
}

@Composable
fun SystemUIControllerComponent(systemUiState: MutableState<SystemUIState>) {
    val context = LocalContext.current
    val view = LocalView.current
    DisposableEffect(systemUiState) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose { }
        val systemUiController = SystemUIController(window = activity.window, view = view)
        when (systemUiState.value) {
            SystemUIState.SYSTEM_UI_HIDDEN -> systemUiController.hideSystemUi()
            SystemUIState.SYSTEM_UI_VISIBLE -> systemUiController.showSystemUi()
        }
        onDispose {
            systemUiController.showSystemUi()
        }
    }
}