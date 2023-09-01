package app.mynta.template.android.core.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import app.mynta.template.android.R
import coil.compose.rememberAsyncImagePainter

@Composable
fun ClickableIcon(
    modifier: Modifier,
    enabled: Boolean = true,
    url: String,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        content = {
            Icon(
                painter = rememberAsyncImagePainter(
                    model = url,
                    placeholder = painterResource(
                        id = R.drawable.icon_circle_outline
                    ),
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.High,
                ),
                contentDescription = null,
                tint = tint
            )
        }
    )
}