package io.nosyntax.foundation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import io.nosyntax.foundation.core.extension.findActivity

@Composable
fun ScreenOrientationController(orientation: Int) {
    val context = LocalContext.current
    val activity = context.findActivity()

    DisposableEffect(orientation) {
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}