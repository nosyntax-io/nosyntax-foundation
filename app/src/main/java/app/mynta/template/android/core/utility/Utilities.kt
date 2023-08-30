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
            "youtube" to R.drawable.icon_youtube_filled,
            "facebook" to R.drawable.icon_facebook_filled,
            "instagram" to R.drawable.icon_instagram_filled,
            "twitter" to R.drawable.icon_twitter_filled,
            "pinterest" to R.drawable.icon_pinterest_filled,
            "netflix" to R.drawable.icon_netflix_filled,
            "settings" to R.drawable.icon_gear_filled,
            "about" to R.drawable.icon_info_filled
        )
        return iconMappings[iconName]
    }
}