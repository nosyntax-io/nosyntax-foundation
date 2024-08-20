package io.nosyntax.foundation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import io.nosyntax.foundation.core.util.Utilities.findActivity

enum class SystemUIState {
    SYSTEM_UI_VISIBLE,
    SYSTEM_UI_HIDDEN
}

@Composable
fun SystemUIController(systemUiState: MutableState<SystemUIState>) {
    val context = LocalContext.current
    val view = LocalView.current
    val activity = context.findActivity()
    val insetsController = activity?.let {
        WindowInsetsControllerCompat(it.window, view)
    }

    DisposableEffect(systemUiState) {
        insetsController?.let {
            when (systemUiState.value) {
                SystemUIState.SYSTEM_UI_VISIBLE -> {
                    it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    it.show(WindowInsetsCompat.Type.systemBars())
                }
                SystemUIState.SYSTEM_UI_HIDDEN -> {
                    it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    it.hide(WindowInsetsCompat.Type.systemBars())
                }
            }
        }
        onDispose {
            insetsController?.apply {
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }
}