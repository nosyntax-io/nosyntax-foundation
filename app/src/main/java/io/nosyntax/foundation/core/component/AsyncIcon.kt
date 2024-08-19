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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.core.utility.ThemePreviews
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun AsyncIcon(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    tint: Color = LocalContentColor.current,
    placeholderTint: Color = LocalContentColor.current.copy(alpha = .4f)
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        placeholder = painterResource(id = R.drawable.icon_circle_outline),
        contentScale = ContentScale.Fit,
        filterQuality = FilterQuality.High
    )

    val iconTint = (painter.state as? AsyncImagePainter.State.Loading)?.let {
        placeholderTint
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
            AsyncIcon(
                modifier = Modifier.size(80.dp),
                url = "https://img.icons8.com/ios-glyphs/100/circled.png",
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}