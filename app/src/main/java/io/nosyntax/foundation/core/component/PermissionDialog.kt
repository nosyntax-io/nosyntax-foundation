package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.nosyntax.foundation.R
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun PermissionDialog(
    icon: Painter,
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.large
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .85f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(id = R.string.grant_permission),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun PermissionDialogPreview() {
    DynamicTheme {
        PermissionDialog(
            icon = painterResource(R.drawable.icon_circle_outline),
            title = "Permission Required",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            onDismiss = { },
            onConfirm = { }
        )
    }
}