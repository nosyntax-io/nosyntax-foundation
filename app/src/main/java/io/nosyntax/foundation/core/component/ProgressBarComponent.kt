package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.utility.Utilities.setColorContrast
import io.nosyntax.foundation.ui.theme.DynamicTheme
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
fun AnimatedProgressBar(
    modifier: Modifier = Modifier,
    source: LottieCompositionSpec,
    color: Color = MaterialTheme.colorScheme.primary,
    iterations: Int = LottieConstants.IterateForever,
    path: Array<String> = arrayOf("**"),
) {
    val composition by rememberLottieComposition(spec = source)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = color.toArgb(),
            keyPath = path
        )
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
        dynamicProperties = dynamicProperties
    )
}

@Preview
@Composable
fun AnimatedProgressBarPreview() {
    DynamicTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedProgressBar(
                modifier = Modifier.size(30.dp),
                source = LottieCompositionSpec.Url(
                    "https://lottie.host/b668a47d-6c6a-4016-b065-d7148975cd91/b8d2lCbOmv.json"
                )
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    color: Color = setColorContrast(
        isDark = isSystemInDarkTheme(),
        color = MaterialTheme.colorScheme.surface
    )
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