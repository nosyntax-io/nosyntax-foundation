package app.mynta.template.android.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.mynta.template.android.ui.theme.DynamicTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    CircularProgressIndicator(
        modifier = modifier
            .padding(20.dp)
            .size(25.dp),
        color = color,
        strokeWidth = 2.5.dp
    )
}

@Preview
@Composable
fun CircularProgressBarPreview() {
    DynamicTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            CircularProgressBar()
        }
    }
}