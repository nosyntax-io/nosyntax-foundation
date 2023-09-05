package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object Utilities {
    fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}