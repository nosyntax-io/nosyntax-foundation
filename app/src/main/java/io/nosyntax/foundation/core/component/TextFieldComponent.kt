package io.nosyntax.foundation.core.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.ui.theme.DynamicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    placeholder: String = "",
    defaultValue: String = "",
    onValueChange: (String) -> Unit
) {
    val inputValue = remember { mutableStateOf(defaultValue) }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = inputValue.value,
        onValueChange = {
            inputValue.value = it
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .border(0.dp, Color.Transparent)
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                focusedIndicatorLineThickness = 0.dp,
                unfocusedIndicatorLineThickness = 0.dp
            ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true
    ) {
        TextFieldDefaults.DecorationBox(
            value = inputValue.value,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.medium,
            visualTransformation = VisualTransformation.None,
            trailingIcon = { /* ... */ },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                )
            },
            interactionSource = interactionSource,
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp
            )
        )
    }
}

@Previews
@Composable
fun TextFieldPreview() {
    DynamicTheme {
        TextField(
            placeholder = "Enter Text",
            onValueChange = { }
        )
    }
}