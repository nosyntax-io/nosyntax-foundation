package io.nosyntax.foundation.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.util.ThemePreviews
import io.nosyntax.foundation.presentation.theme.DynamicTheme

@Composable
fun Snackbar(
    message: String,
    isRtl: Boolean = true
) {
    Snackbar(
        modifier = Modifier.padding(10.dp).alpha(.8f),
        shape = MaterialTheme.shapes.medium,
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@ThemePreviews
@Composable
fun SnackbarPreview() {
    DynamicTheme {
        Snackbar(
            message = "Lorem Ipsum is simply dummy text of the printing."
        )
    }
}