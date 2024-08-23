package io.nosyntax.foundation.presentation.screen.web.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.util.Previews
import io.nosyntax.foundation.presentation.theme.FoundationTheme

@Composable
fun JsAlertDialog(
    message: String,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onConfirm,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {
                Text(text = stringResource(id = R.string.i_understand))
            }
        }
    }
}

@Previews
@Composable
fun JsAlertDialogPreview() {
    FoundationTheme {
        JsAlertDialog(
            message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            onConfirm = { }
        )
    }
}