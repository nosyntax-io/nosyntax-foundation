package io.nosyntax.foundation.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import io.nosyntax.foundation.R
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun Image(
    modifier: Modifier,
    source: Any,
    contentScale: ContentScale = ContentScale.Crop
) {
    when (source) {
        is String -> AsyncImage(
            modifier = modifier,
            url = source,
            contentScale = contentScale
        )
        is Painter -> Image(
            modifier = modifier,
            painter = source,
            contentDescription = null,
            contentScale = contentScale
        )
    }
}

@Composable
private fun AsyncImage(
    modifier: Modifier,
    url: String,
    contentScale: ContentScale
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
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            Box(modifier = Modifier.fillMaxSize().shimmerEffect())
        }
    }
}

@Preview
@Composable
fun ImagePreview() {
    DynamicTheme(darkTheme = false) {
        Image(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            source = "https://via.placeholder.com/700x300"
        )
    }
}