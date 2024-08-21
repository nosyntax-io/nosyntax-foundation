package io.nosyntax.foundation.core.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.util.ThemePreviews
import io.nosyntax.foundation.presentation.theme.FoundationTheme

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition("shimmer_transition")

    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000)
        ),
        label = "shimmer_animation"
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors(
                baseColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            start = Offset(
                x = startOffsetX,
                y = 0f
            ),
            end = Offset(
                x = startOffsetX + size.width.toFloat(),
                y = size.height.toFloat()
            )
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

private fun shimmerColors(baseColor: Color): List<Color> {
    return listOf(baseColor, baseColor.copy(alpha = .3f), baseColor)
}

@ThemePreviews
@Composable
fun ShimmerPreview() {
    FoundationTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(MaterialTheme.colorScheme.background)
                .shimmerEffect()
        )
    }
}