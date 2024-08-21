package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.core.util.ThemePreviews
import io.nosyntax.foundation.presentation.theme.FoundationTheme

@Composable
fun AsyncIcon(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    placeholder: Painter = painterResource(id = R.drawable.icon_circle_outline),
    tint: Color = LocalContentColor.current,
    placeholderTint: Color = LocalContentColor.current.copy(alpha = .4f)
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        placeholder = placeholder,
        contentScale = ContentScale.Fit,
        filterQuality = FilterQuality.High
    )

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = when (painter.state) {
            is AsyncImagePainter.State.Loading -> placeholderTint
            else -> tint
        }
    )
}

@ThemePreviews
@Composable
fun IconPreview() {
    FoundationTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            AsyncIcon(
                url = "https://img.icons8.com/ios-glyphs/100/circled.png",
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}