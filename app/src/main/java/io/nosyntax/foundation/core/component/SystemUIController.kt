package io.nosyntax.foundation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import io.nosyntax.foundation.core.extension.findActivity

enum class SystemUIState {
    SYSTEM_UI_VISIBLE,
    SYSTEM_UI_HIDDEN
}

@Composable
fun SystemUIController(systemUiState: MutableState<SystemUIState>) {
    val context = LocalContext.current
    val view = LocalView.current

    val insetsController = WindowInsetsControllerCompat(context.findActivity().window, view)

    DisposableEffect(systemUiState) {
        when (systemUiState.value) {
            SystemUIState.SYSTEM_UI_VISIBLE -> {
                insetsController.showSystemBars()
            }
            SystemUIState.SYSTEM_UI_HIDDEN -> {
                insetsController.hideSystemBars()
            }
        }
        onDispose {
            insetsController.showSystemBars()
        }
    }
}

private fun WindowInsetsControllerCompat.showSystemBars() {
    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    show(WindowInsetsCompat.Type.systemBars())
}

private fun WindowInsetsControllerCompat.hideSystemBars() {
    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    hide(WindowInsetsCompat.Type.systemBars())
}