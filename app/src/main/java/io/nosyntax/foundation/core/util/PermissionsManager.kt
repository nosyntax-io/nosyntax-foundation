package io.nosyntax.foundation.core.util

import android.content.Context
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.nosyntax.foundation.core.extension.openAppSettings

@OptIn(ExperimentalPermissionsApi::class)
class PermissionsManager(private val context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    /**
     * Requests a single permission. It Checks if the permission is being asked for the first time,
     * if a rationale should be shown, or if the user should go to settings to grant the permission manually.
     *
     * @param permissionState The state of the permission being requested.
     */
    fun requestPermission(permissionState: PermissionState) {
        when {
            isFirstRequest(permissionState.permission) -> {
                setFirstRequest(permissionState.permission)
                permissionState.launchPermissionRequest()
            }

            permissionState.status.shouldShowRationale -> {
                permissionState.launchPermissionRequest()
            }

            else -> {
                context.openAppSettings()
            }
        }
    }

    /**
     * Requests multiple permissions. It filters out permissions that are being requested for the first time
     * and manages whether to show a rationale or direct the user to app settings for manual granting.
     *
     *  @param permissionsState The current state of the permissions being requested.
     */
    fun requestMultiplePermissions(permissionsState: MultiplePermissionsState) {
        val permissionsToRequest = permissionsState.permissions.filter {
            isFirstRequest(it.permission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionsToRequest.forEach { permissionState ->
                setFirstRequest(permissionState.permission)
            }
            permissionsState.launchMultiplePermissionRequest()
        } else if (permissionsState.shouldShowRationale) {
            permissionsState.launchMultiplePermissionRequest()
        } else {
            context.openAppSettings()
        }
    }

    /**
     * Checks if the permission is being requested for the first time by retrieving
     * its status from shared preferences.
     *
     * @param permission The permission to check.
     * @return True if it's the first request, false otherwise.
     */
    private fun isFirstRequest(permission: String): Boolean =
        preferences.getBoolean(permission, true)

    /**
     * Updates shared preferences to mark a permission as having been requested.
     *
     * @param permission The permission to update.
     */
    private fun setFirstRequest(permission: String) {
        preferences.edit().putBoolean(permission, false).apply()
    }

    companion object {
        private const val PREFS_FILE_NAME = "permissions"
    }
}