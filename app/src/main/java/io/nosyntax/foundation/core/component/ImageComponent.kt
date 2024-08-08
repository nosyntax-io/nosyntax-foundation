package io.nosyntax.foundation.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.core.utility.ThemePreviews
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun Image(
    modifier: Modifier = Modifier,
    source: Any,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    when (source) {
        is String -> AsyncImage(
            modifier = modifier,
            url = source,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
        is Painter -> Image(
            modifier = modifier,
            painter = source,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}

@Composable
private fun AsyncImage(
    modifier: Modifier,
    url: String,
    contentDescription: String?,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        contentScale = contentScale,
        filterQuality = FilterQuality.High
    )

    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription,
            alignment = alignment,
            contentScale = ContentScale.Crop,
            alpha = alpha,
            colorFilter = colorFilter
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            Box(modifier = Modifier.fillMaxSize().shimmerEffect())
        }
    }
}

@ThemePreviews
@Composable
fun ImagePreview() {
    DynamicTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Image(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                source = "https://via.placeholder.com/700x300"
            )
        }
    }
}