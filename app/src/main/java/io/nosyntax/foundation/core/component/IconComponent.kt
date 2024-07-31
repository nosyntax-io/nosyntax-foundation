package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.Utilities.setColorContrast
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun Icon(
    modifier: Modifier,
    source: Any,
    tint: Color = LocalContentColor.current,
) {
    when (source) {
        is String -> {
            AsyncImageIcon(
                modifier = modifier,
                url = source,
                tint = tint
            )
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
fun ClickableIcon(
    modifier: Modifier,
    source: Any,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            modifier = modifier,
            source = source,
            tint = tint
        )
    }
}

@Composable
private fun AsyncImageIcon(
    modifier: Modifier,
    url: String,
    tint: Color = LocalContentColor.current
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        placeholder = painterResource(id = R.drawable.icon_circle_outline),
        contentScale = ContentScale.Fit,
        filterQuality = FilterQuality.High
    )

    val iconTint = (painter.state as? AsyncImagePainter.State.Loading)?.let {
        setColorContrast(isSystemInDarkTheme(), MaterialTheme.colorScheme.surface)
    } ?: tint

    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = null,
        tint = iconTint
    )
}

@Composable
@Preview
fun IconPreview() {
    DynamicTheme(darkTheme = false) {
        Icon(
            modifier = Modifier.size(80.dp),
            source = painterResource(id = R.drawable.icon_circle_outline),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
@Preview
fun ClickableIconPreview() {
    DynamicTheme(darkTheme = false) {
        ClickableIcon(
            modifier = Modifier.size(80.dp),
            source = painterResource(id = R.drawable.icon_circle_outline),
            tint = MaterialTheme.colorScheme.onBackground,
            onClick = { }
        )
    }
}