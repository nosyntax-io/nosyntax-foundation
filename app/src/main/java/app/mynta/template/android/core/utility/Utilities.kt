package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

object Utilities {
    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}