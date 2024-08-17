package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.ui.theme.DynamicTheme
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import io.nosyntax.foundation.core.utility.ThemePreviews

@Composable
fun LottieAnimated(
    source: LottieCompositionSpec,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
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
        composition = composition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties,
        safeMode = true
    )
}

@ThemePreviews
@Composable
fun LottieAnimatedPreview() {
    DynamicTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            LottieAnimated(
                modifier = Modifier.size(30.dp),
                source = LottieCompositionSpec.Url(
                    url = "https://lottie.host/b668a47d-6c6a-4016-b065-d7148975cd91/b8d2lCbOmv.json"
                )
            )
        }
    }
}