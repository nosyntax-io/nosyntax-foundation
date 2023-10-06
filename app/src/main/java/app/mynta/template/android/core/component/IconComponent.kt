package app.mynta.template.android.core.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import app.mynta.template.android.R
import app.mynta.template.android.core.utility.Utilities.setColorContrast
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun DynamicIcon(
    modifier: Modifier,
    source: Any,
    tint: Color = LocalContentColor.current,
) {
    when (source) {
        is String -> {
            IconWithAsyncImage(modifier = modifier, url = source, tint = tint)
        }
        is Painter -> {
            Icon(
                modifier = modifier,
                painter = source,
                contentDescription = null,
                tint = tint
            )
        }
    }
}

@Composable
fun DynamicClickableIcon(
    modifier: Modifier,
    source: Any,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    IconButton(
        modifier = Modifier,
        enabled = enabled,
        onClick = onClick,
        content = {
            when (source) {
                is String -> {
                    IconWithAsyncImage(modifier = modifier, url = source, tint = tint)
                }
                is Painter -> {
                    Icon(
                        modifier = modifier,
                        painter = source,
                        contentDescription = null,
                        tint = tint
                    )
                }
            }
        }
    )
}

@Composable
private fun IconWithAsyncImage(
    modifier: Modifier,
    url: String,
    tint: Color = LocalContentColor.current
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        placeholder = painterResource(
            id = R.drawable.icon_circle_outline
        ),
        contentScale = ContentScale.Fit,
        filterQuality = FilterQuality.High
    )
    val iconTint = if (painter.state is AsyncImagePainter.State.Loading) {
        setColorContrast(isSystemInDarkTheme(), MaterialTheme.colorScheme.surface)
    } else {
        tint
    }

    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = null,
        tint = iconTint
    )
}