package io.nosyntax.foundation.presentation.web.utility

import android.content.Context

object PermissionUtil {
    private const val PREFS_FILE_NAME = "permissions"

    private fun getPreferences(context: Context) =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    fun isFirstRequest(context: Context, permission: String): Boolean =
        getPreferences(context).getBoolean(permission, true)

    fun setFirstRequest(context: Context, permission: String, isFirstTime: Boolean) {
        getPreferences(context).edit().putBoolean(permission, isFirstTime).apply()
    }
}