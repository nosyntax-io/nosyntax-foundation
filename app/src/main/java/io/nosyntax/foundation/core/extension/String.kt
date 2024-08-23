package io.nosyntax.foundation.core.extension

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

/**
 * Converts a hexadecimal color string (e.g., "#FFFFFF") to a Compose [Color].
 *
 * @return A [Color] object representing the parsed color.
 * @throws IllegalArgumentException if the string is not a valid color.
 */
fun String.toColor(): Color = Color(parseColor(this))

/**
 * Checks if the current route is considered a top-level route.
 *
 * This function helps determine whether the current route should have a specific UI representation
 * or behavior based on its level in the navigation hierarchy.
 *
 * @return True if the route is a top-level route; false otherwise.
 */
fun String.isTopLevelRoute(): Boolean =
    !listOf("settings", "about").any { this.startsWith(it) }