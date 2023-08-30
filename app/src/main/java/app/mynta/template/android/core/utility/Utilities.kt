package app.mynta.template.android.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import app.mynta.template.android.R

object Utilities {
    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun getIconResourceId(iconName: String): Int? {
        val iconMappings = mapOf(
            "web" to R.drawable.icon_chevron_right_outline,
        )
        return iconMappings[iconName]
    }
}