package io.nosyntax.foundation.core.component

import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun SnackbarComponent(
    message: String,
    isRtl: Boolean = true
) {
    Snackbar(
        modifier = Modifier.padding(10.dp).alpha(.8f),
        containerColor = Color.Black,
        contentColor = Color.White,
        shape = MaterialTheme.shapes.medium
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun SnackbarComponentPreview() {
    DynamicTheme {
        SnackbarComponent(
            message = "Lorem Ipsum is simply dummy text of the printing.",
            isRtl = true
        )
    }
}