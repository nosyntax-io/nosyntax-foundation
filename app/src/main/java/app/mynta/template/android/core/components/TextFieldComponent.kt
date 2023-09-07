package app.mynta.template.android.core.components

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.mynta.template.android.ui.theme.DynamicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(placeholder: String = "", defaultValue: String = "", onValueChange: (String) -> Unit) {
    val inputValue = remember { mutableStateOf(defaultValue) }

    BasicTextField(
        value = inputValue.value,
        onValueChange = {
            inputValue.value = it
            onValueChange(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = MutableInteractionSource(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                focusedIndicatorLineThickness = 0.dp,
                unfocusedIndicatorLineThickness = 0.dp
            )
            .fillMaxWidth()
            .border(width = 0.dp, Color.Transparent)
            .height(45.dp),
        interactionSource = MutableInteractionSource(),
        enabled = true,
        singleLine = true
    ) {
        TextFieldDefaults.DecorationBox(
            value = inputValue.value,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
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
                    modifier = Modifier.alpha(.7f),
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            interactionSource = MutableInteractionSource(),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp
            )
        )
    }
}

@Composable
@Preview
fun TextFieldPreview() {
    DynamicTheme(darkTheme = true) {
        TextFieldComponent(
            placeholder = "Enter Text",
            onValueChange = { }
        )
    }
}