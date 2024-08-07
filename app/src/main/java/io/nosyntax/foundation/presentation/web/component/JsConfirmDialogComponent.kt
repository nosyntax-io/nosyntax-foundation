package io.nosyntax.foundation.presentation.web.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun JsConfirmDialog(
    message: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(0.dp),
                        onClick = onCancel
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            modifier = Modifier.padding(horizontal = 15.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(0.dp),
                        onClick = onConfirm
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            modifier = Modifier.padding(horizontal = 15.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    )
}

@Previews
@Composable
fun JsConfirmDialogPreview() {
    DynamicTheme {
        JsConfirmDialog(
            message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            onCancel = { },
            onConfirm = { }
        )
    }
}