package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun setTintedIcon(isDark: Boolean, color: Color): Color {
        val colorModifier = if (isDark) 0.5f else -0.1f
        val tintedColor = Color(
            red = color.red * (1 + colorModifier),
            green = color.green * (1 + colorModifier),
            blue = color.blue * (1 + colorModifier)
        )
        return tintedColor.copy(alpha = 1f)
    }

    fun getCurrentYear(): String {
        val calendar = Calendar.getInstance().time
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar)
    }

    fun isUrlValid(url: String): Boolean {
        return url.startsWith("http://")
            || url.startsWith("https://")
            || url.startsWith("file://")
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}