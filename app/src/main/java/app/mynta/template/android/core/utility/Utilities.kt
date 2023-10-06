package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun setColorContrast(isDark: Boolean, color: Color): Color {
        return if (isDark) {
            Color(ColorUtils.blendARGB(color.toArgb(), Color.White.toArgb(), 0.2f))
        } else {
            Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.1f))
        }
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