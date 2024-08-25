package io.nosyntax.foundation.presentation.screen.web.util

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import io.nosyntax.foundation.core.util.PermissionsManager

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberWebViewPermissions(): WebViewPermissions {
    val context = LocalContext.current

    val permissionsManager = remember { PermissionsManager(context) }

    val storagePermissionState = rememberPermissionState(
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    return remember {
        WebViewPermissions(
            permissionsManager,
            storagePermissionState,
            cameraPermissionState,
            locationPermissionsState
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
class WebViewPermissions(
    private val permissionsManager: PermissionsManager,
    private val storagePermissionState: PermissionState,
    private val cameraPermissionState: PermissionState,
    private val locationPermissionsState: MultiplePermissionsState
) {
    private val _rationaleState = mutableStateOf<RationaleType?>(null)
    val rationaleState: State<RationaleType?> get() = _rationaleState

    fun isStoragePermissionGranted(): Boolean {
        return storagePermissionState.status.isGranted
    }

    fun showStoragePermissionRationale() {
        _rationaleState.value = RationaleType.StorageRequired
    }

    fun requestStoragePermission() {
        permissionsManager.requestPermission(
            permissionState = storagePermissionState
        )
    }

    fun isCameraPermissionGranted(): Boolean {
        return cameraPermissionState.status.isGranted
    }

    fun showCameraPermissionRationale() {
        _rationaleState.value = RationaleType.CameraRequired
    }

    fun requestCameraPermission() {
        permissionsManager.requestPermission(
            permissionState = cameraPermissionState
        )
    }

    fun isLocationPermissionsGranted(): Boolean {
        return locationPermissionsState.allPermissionsGranted
    }

    fun showLocationPermissionsRationale() {
        _rationaleState.value = RationaleType.LocationRequired
    }

    fun requestLocationPermissions() {
        permissionsManager.requestMultiplePermissions(
            permissionsState = locationPermissionsState
        )
    }

    fun dismissRationale() {
        _rationaleState.value = null
    }

    sealed class RationaleType {
        object StorageRequired : RationaleType()
        object CameraRequired : RationaleType()
        object LocationRequired : RationaleType()
    }
}