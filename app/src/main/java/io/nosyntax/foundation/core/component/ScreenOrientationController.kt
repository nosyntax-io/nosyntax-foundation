package io.nosyntax.foundation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import io.nosyntax.foundation.core.utility.Utilities.findActivity

@Composable
fun ScreenOrientationController(orientation: Int) {
    val context = LocalContext.current
    val activity = context.findActivity()

    DisposableEffect(orientation) {
        activity?.let {
            val originalOrientation = it.requestedOrientation
            it.requestedOrientation = orientation
            onDispose {
                it.requestedOrientation = originalOrientation
            }
        } ?: onDispose { }
    }
}