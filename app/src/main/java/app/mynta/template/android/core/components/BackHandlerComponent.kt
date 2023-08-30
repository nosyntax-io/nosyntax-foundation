package app.mynta.template.android.core.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun BackActionHandler(
    canGoBack: Boolean,
    isDrawerOpen: Boolean,
    onGoBack: () -> Unit
) {
    BackHandler(enabled = canGoBack && !isDrawerOpen) {
        if (canGoBack) {
            onGoBack()
        }
    }
}