package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun getCurrentYear(): String {
        val calendar = Calendar.getInstance().time
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar)
    }

    fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}