package io.nosyntax.foundation.core.extension

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

fun String.toColor(): Color = Color(parseColor(this))