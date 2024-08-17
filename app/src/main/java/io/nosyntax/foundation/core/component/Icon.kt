package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.Utilities.setColorContrast
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.core.utility.ThemePreviews
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun Icon(
    source: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    when (source) {
        is String -> {
            AsyncIcon(
                url = source,
                contentDescription = contentDescription,
                modifier = modifier,
                tint = tint
            )
        }
        is Painter -> {
            Icon(
                painter = source,
                contentDescription = contentDescription,
                modifier = modifier,
                tint = tint
            )
        }
    }
}

@Composable
private fun AsyncIcon(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
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
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = iconTint
    )
}

@ThemePreviews
@Composable
fun IconPreview() {
    DynamicTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Icon(
                modifier = Modifier.size(80.dp),
                source = painterResource(id = R.drawable.icon_circle_outline),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}