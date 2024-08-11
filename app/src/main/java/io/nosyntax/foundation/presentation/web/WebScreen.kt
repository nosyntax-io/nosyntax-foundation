package io.nosyntax.foundation.presentation.web

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.GeolocationPermissions
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.ScreenOrientationController
import io.nosyntax.foundation.core.component.NoConnectionView
import io.nosyntax.foundation.core.component.PermissionDialog
import io.nosyntax.foundation.core.component.SystemUIController
import io.nosyntax.foundation.core.component.SystemUIState
import io.nosyntax.foundation.core.utility.Connectivity
import io.nosyntax.foundation.core.utility.Downloader
import io.nosyntax.foundation.core.utility.Utilities.findActivity
import io.nosyntax.foundation.core.utility.WebKitChromeClient
import io.nosyntax.foundation.core.utility.WebKitClient
import io.nosyntax.foundation.core.utility.WebView
import io.nosyntax.foundation.core.utility.handleIntent
import io.nosyntax.foundation.core.utility.monetize.BannerAd
import io.nosyntax.foundation.core.utility.openAppSettings
import io.nosyntax.foundation.core.utility.openContent
import io.nosyntax.foundation.core.utility.rememberSaveableWebViewState
import io.nosyntax.foundation.core.utility.rememberWebViewNavigator
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.main.MainActivity
import io.nosyntax.foundation.presentation.web.component.JsAlertDialog
import io.nosyntax.foundation.presentation.web.component.JsConfirmDialog
import io.nosyntax.foundation.presentation.web.component.JsDialog
import io.nosyntax.foundation.presentation.web.component.JsPromptDialog
import io.nosyntax.foundation.presentation.web.component.LoadingIndicator
import io.nosyntax.foundation.presentation.web.utility.FileChooserDelegate
import io.nosyntax.foundation.presentation.web.utility.JavaScriptInterface
import io.nosyntax.foundation.presentation.web.utility.PermissionUtil
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(
    appConfig: AppConfig,
    url: String,
    captureBackPresses: Boolean
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val webViewState = rememberSaveableWebViewState()
    val navigator = rememberWebViewNavigator()

    val storagePermissionState = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    var showStoragePermissionDialog by rememberSaveable { mutableStateOf(false) }
    var showLocationPermissionDialog by rememberSaveable { mutableStateOf(false) }
    var showNoConnectionDialog by rememberSaveable { mutableStateOf(false) }

    val systemUiState = remember { mutableStateOf(SystemUIState.SYSTEM_UI_VISIBLE) }
    var requestedOrientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }
    var jsDialogInfo by rememberSaveable { mutableStateOf<Pair<JsDialog?, JsResult?>?>(null) }
    var customWebView by rememberSaveable { mutableStateOf<View?>(null) }
    var customWebViewCallback by rememberSaveable { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }
    var totalLoadedPages by remember { mutableIntStateOf(0) }

    SystemUIController(systemUiState = systemUiState)
    ScreenOrientationController(orientation = requestedOrientation)

    LaunchedEffect(navigator) {
        if (webViewState.viewState == null) {
            navigator.loadUrl(url)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        WebView(
            modifier = Modifier.fillMaxSize(),
            state = webViewState,
            navigator = navigator,
            captureBackPresses = captureBackPresses,
            onCreated = { webView ->
                initWebView(
                    webView = webView,
                    coroutineScope = coroutineScope,
                    onDownloadRequest = { fileName, url, userAgent, mimeType ->
                        if (Build.VERSION.SDK_INT in 24..29) {
                            if (storagePermissionState.status.isGranted) {
                                Downloader(context).download(fileName, url, userAgent, mimeType)
                            } else {
                                showStoragePermissionDialog = true
                            }
                        } else {
                            Downloader(context).download(fileName, url, userAgent, mimeType)
                        }
                    }
                )
            },
            client = webClient(
                onPageLoaded = {
                    if (totalLoadedPages < 7) {
                        totalLoadedPages++
                    } else {
                        (context.findActivity() as MainActivity).showInterstitial()
                        totalLoadedPages = 1
                    }
                },
                onReceivedError = {
                    showNoConnectionDialog = true
                }
            ),
            chromeClient = chromeClient(
                context = context,
                onJsDialogEvent = { dialog, result ->
                    jsDialogInfo = dialog to result
                },
                onCustomViewShown = { view, callback ->
                    if (customWebView != null) {
                        systemUiState.value = SystemUIState.SYSTEM_UI_VISIBLE
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                        customWebView = null
                        customWebViewCallback?.onCustomViewHidden()
                        customWebViewCallback = null
                    } else {
                        customWebView = view
                        customWebViewCallback = callback

                        systemUiState.value = SystemUIState.SYSTEM_UI_HIDDEN
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                },
                onCustomViewHidden = {
                    if (customWebView != null) {
                        systemUiState.value = SystemUIState.SYSTEM_UI_VISIBLE
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                        customWebView = null
                        customWebViewCallback?.onCustomViewHidden()
                        customWebViewCallback = null
                    }
                },
                onGeolocationPrompt = { origin, callback ->
                    if (locationPermissionsState.allPermissionsGranted) {
                        callback?.invoke(origin, true, false)
                    } else {
                        callback?.invoke(origin, false, false)
                        showLocationPermissionDialog = true
                    }
                }
            )
        )

        val ads = appConfig.monetization.ads
        BannerAd(
            enabled = ads.enabled && ads.bannerDisplay, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }

    if (customWebView != null) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { mContext ->
            val frameLayout = FrameLayout(mContext)
            frameLayout.addView(customWebView)
            frameLayout
        })
    }

    if (webViewState.isLoading) {
        val indicatorConfig = appConfig.components.loadingIndicator
        if (indicatorConfig.visible) {
            LoadingIndicator(indicatorConfig = indicatorConfig)
        }
    }

    if (showStoragePermissionDialog) {
        PermissionDialog(
            icon = painterResource(R.drawable.icon_folder_outline),
            title = stringResource(R.string.storage_required),
            description = stringResource(R.string.storage_required_description),
            onDismiss = { showStoragePermissionDialog = false },
            onConfirm = {
                showStoragePermissionDialog = false
                when {
                    PermissionUtil.isFirstRequest(context, storagePermissionState.permission) -> {
                        PermissionUtil.setFirstRequest(
                            context = context,
                            permission = storagePermissionState.permission,
                            isFirstTime = false
                        )
                        storagePermissionState.launchPermissionRequest()
                    }
                    storagePermissionState.status.shouldShowRationale -> {
                        storagePermissionState.launchPermissionRequest()
                    }
                    else -> {
                        context.openAppSettings()
                    }
                }
            }
        )
    }

    if (showLocationPermissionDialog) {
        PermissionDialog(
            icon = painterResource(R.drawable.icon_location_outline),
            title = stringResource(R.string.location_required),
            description = stringResource(R.string.location_required_description),
            onDismiss = { showLocationPermissionDialog = false },
            onConfirm = {
                showLocationPermissionDialog = false
                when {
                    locationPermissionsState.permissions.any { permissionState ->
                        PermissionUtil.isFirstRequest(
                            context = context,
                            permission = permissionState.permission
                        )
                    } -> {
                        locationPermissionsState.permissions.forEach { permission ->
                            PermissionUtil.setFirstRequest(
                                context = context,
                                permission.permission,
                                isFirstTime = false
                            )
                        }
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                    locationPermissionsState.shouldShowRationale -> {
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                    else -> {
                        context.openAppSettings()
                    }
                }
            }
        )
    }

    // TODO: Reset connection state when update navigate.
    // TODO: Hide internal no internet connection page
    if (showNoConnectionDialog) {
        NoConnectionView(onRetry = {
            if (Connectivity.getInstance().isOnline()) {
                showNoConnectionDialog = false
                navigator.reload()
            }
        })
    }

    jsDialogInfo?.let { (dialog, result) ->
        val cancelDialog: () -> Unit = {
            jsDialogInfo = null
            result?.cancel()
        }
        val confirmDialog: () -> Unit = {
            jsDialogInfo = null
            result?.confirm()
        }

        when (dialog) {
            is JsDialog.Alert -> {
                JsAlertDialog(
                    message = dialog.message,
                    onConfirm = confirmDialog
                )
            }
            is JsDialog.Confirm -> {
                JsConfirmDialog(
                    message = dialog.message,
                    onCancel = cancelDialog,
                    onConfirm = confirmDialog
                )
            }
            is JsDialog.Prompt -> {
                JsPromptDialog(
                    message = dialog.message,
                    defaultValue = dialog.defaultValue,
                    onCancel = cancelDialog,
                    onConfirm = { promptResult ->
                        if (result is JsPromptResult) {
                            result.confirm(promptResult)
                        }
                        cancelDialog()
                    }
                )
            }
            else -> {}
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun initWebView(
    webView: WebView,
    coroutineScope: CoroutineScope,
    onDownloadRequest: (String, String, String?, String) -> Unit
) {
    webView.apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false

        settings.apply {
            javaScriptEnabled = Constants.WEB_JAVASCRIPT_OPTION
            allowFileAccess = Constants.WEB_ALLOW_FILE_ACCESS
            allowContentAccess = Constants.WEB_ALLOW_CONTENT_ACCESS
            domStorageEnabled = Constants.WEB_DOM_STORAGE_ENABLED
            databaseEnabled = Constants.WEB_DATABASE_ENABLED
            javaScriptCanOpenWindowsAutomatically = Constants.JAVASCRIPT_CAN_OPEN_WINDOWS_AUTOMATICALLY
            cacheMode = WebSettings.LOAD_DEFAULT
            supportMultipleWindows()
            setGeolocationEnabled(Constants.WEB_SET_GEOLOCATION_ENABLED)
        }

        addJavascriptInterface(
            JavaScriptInterface(
                context = context,
                coroutineScope = coroutineScope
            ), "io"
        )

        setDownloadListener { url, userAgent, disposition, mimeType, _ ->
            val fileName = URLUtil.guessFileName(url, disposition, mimeType)
            onDownloadRequest(fileName, url, userAgent, mimeType)
        }
    }
}

@Composable
fun webClient(
    onPageLoaded: () -> Unit,
    onReceivedError: () -> Unit
): WebKitClient {
    val context = LocalContext.current
    val knownDomains = remember {
        context.assets.open("known-domains.txt").bufferedReader().readLines()
    }

    val webClient = remember {
        object : WebKitClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                onPageLoaded()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString().orEmpty()

                if (url == "about:blank") {
                    return true
                }

                if (!URLUtil.isValidUrl(url)) {
                    context.handleIntent(url)
                    return true
                }

                if (knownDomains.any { url.contains(it, ignoreCase = true) }) {
                    return runCatching { context.openContent(url) }.isSuccess
                }

                return false
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                val networkErrors = listOf(ERROR_CONNECT, ERROR_TIMEOUT, ERROR_HOST_LOOKUP, ERROR_UNKNOWN)
                if (request?.isForMainFrame == true && error?.errorCode in networkErrors) {
                    onReceivedError()
                }
            }
        }
    }

    return webClient
}

@Composable
fun chromeClient(
    context: Context,
    onJsDialogEvent: (JsDialog, JsResult) -> Unit,
    onCustomViewShown: (View, WebChromeClient.CustomViewCallback) -> Unit,
    onCustomViewHidden: () -> Unit,
    onGeolocationPrompt: (origin: String?, callback: GeolocationPermissions.Callback?) -> Unit
): WebKitChromeClient {
    var filePath by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    val fileChooserDelegate = FileChooserDelegate(context) { uris ->
        filePath?.onReceiveValue(uris)
        filePath = null
    }

    val fileChooser = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        fileChooserDelegate.onActivityResult(result.data)
    }

    val chromeClient = remember {
        object: WebKitChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Alert(message), result)
                    return true
                }
                result?.cancel()
                return false
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Confirm(message), result)
                    return true
                }
                result?.cancel()
                return false
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Prompt(message, defaultValue.orEmpty()), result)
                    return true
                }
                result?.cancel()
                return false
            }

            override fun getDefaultVideoPoster(): Bitmap? {
                return BitmapFactory.decodeResource(context.resources, 2130837573)
            }

            override fun onHideCustomView() {
                onCustomViewHidden()
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                if (view != null && callback != null) {
                    onCustomViewShown(view, callback)
                }
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
                onGeolocationPrompt(origin, callback)
            }

            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
                filePath?.onReceiveValue(null)
                filePath = filePathCallback

                val customFileChooserParams = object: FileChooserParams() {
                    override fun getMode() = 1
                    override fun getAcceptTypes() = arrayOf("image/*")
                    override fun isCaptureEnabled() = true
                    override fun getTitle() = fileChooserParams.title
                    override fun getFilenameHint() = fileChooserParams.filenameHint
                    override fun createIntent() = fileChooserParams.createIntent()
                }
                return fileChooserDelegate.onShowFileChooser(customFileChooserParams, fileChooser)
            }
        }
    }

    return chromeClient
}