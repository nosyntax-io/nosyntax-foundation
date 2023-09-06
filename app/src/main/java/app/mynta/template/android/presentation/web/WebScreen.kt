package app.mynta.template.android.presentation.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.http.SslError
import android.view.View
import android.view.ViewGroup
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.components.ChangeScreenOrientationComponent
import app.mynta.template.android.core.components.SystemUIControllerComponent
import app.mynta.template.android.core.components.SystemUIState
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Intents.openDial
import app.mynta.template.android.core.utility.Intents.openEmailFromUrl
import app.mynta.template.android.core.utility.Intents.openPlayStore
import app.mynta.template.android.core.utility.Intents.openSMS
import app.mynta.template.android.presentation.web.components.AlertDialogComponent
import app.mynta.template.android.presentation.web.components.ConfirmDialogComponent
import app.mynta.template.android.core.components.NoConnectionComponent
import app.mynta.template.android.presentation.web.components.JsDialog
import app.mynta.template.android.presentation.web.components.PromptDialogComponent

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(url: String) {
    val systemUiState = remember { mutableStateOf(SystemUIState.SYSTEM_UI_VISIBLE) }
    var requestedOrientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var currentUrl by rememberSaveable { mutableStateOf(url) }

    var jsDialogState by remember { mutableStateOf<Pair<JsDialog?, JsResult?>?>(null) }

    var webCustomView by remember { mutableStateOf<View?>(null) }
    var webCustomViewCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }
    var noConnectionState by remember { mutableStateOf(false) }

    SystemUIControllerComponent(systemUiState = systemUiState)
    ChangeScreenOrientationComponent(orientation = requestedOrientation)

    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {
        webView?.reload()
    })

    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
        .verticalScroll(
            rememberScrollState()
        )) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
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

                    webViewClient = CustomWebViewClient(
                        context = context,
                        onPageStarted = {  },
                        onPageFinished = {  },
                        onUpdateCurrentUrl = { currentUrl = it },
                        onReceiveError = { noConnectionState = true }
                    )

                    webChromeClient = CustomWebChromeClient(context = context,
                        onAlertResult = { message, result ->
                            jsDialogState = JsDialog.Alert(message) to result
                        },
                        onConfirmResult = { message, result ->
                            jsDialogState = JsDialog.Confirm(message) to result
                        },
                        onPromptResult = { message, defaultValue, result ->
                            jsDialogState = JsDialog.Prompt(message, defaultValue) to result
                        },
                        onCustomViewShown = { view, callback ->
                            if (webCustomView != null) {
                                systemUiState.value = SystemUIState.SYSTEM_UI_VISIBLE
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                                webCustomView = null
                                webCustomViewCallback?.onCustomViewHidden()
                                webCustomViewCallback = null
                            } else {
                                webCustomView = view
                                webCustomViewCallback = callback

                                systemUiState.value = SystemUIState.SYSTEM_UI_HIDDEN
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            }
                        },
                        onCustomViewHidden = {
                            if (webCustomView != null) {
                                systemUiState.value = SystemUIState.SYSTEM_UI_VISIBLE
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                                webCustomView = null
                                webCustomViewCallback?.onCustomViewHidden()
                                webCustomViewCallback = null
                            }
                        }
                    )

                    loadUrl(currentUrl)
                    webView = this
                }
            }, update = {
                it.loadUrl(currentUrl)
            }
        )

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullRefreshState,
            refreshing = false,
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary
        )
    }

    if (webCustomView != null) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
            val frameLayout = FrameLayout(context)
            frameLayout.addView(webCustomView)
            frameLayout
        })
    }

    if (noConnectionState) {
        NoConnectionComponent(onRetry = {
            if (Connectivity.getInstance().isOnline()) {
                noConnectionState = false
                webView?.reload()
            }
        })
    }

    jsDialogState?.let { (dialog, result) ->
        when(dialog) {
            is JsDialog.Alert -> {
                AlertDialogComponent(message = dialog.message, onDismiss = {
                    jsDialogState = null
                    result?.confirm()
                })
            }
            is JsDialog.Confirm -> {
                ConfirmDialogComponent(message = dialog.message, onCancel = {
                    jsDialogState = null
                    result?.cancel()
                }, onConfirm = {
                    jsDialogState = null
                    result?.confirm()
                })
            }
            is JsDialog.Prompt -> {
                PromptDialogComponent(message = dialog.message, defaultValue = dialog.defaultValue, onCancel = {
                    jsDialogState = null
                    result?.cancel()
                }, onConfirm = { promptResult ->
                    jsDialogState = null
                    if (result is JsPromptResult) {
                        result.confirm(promptResult)
                    }
                })
            }
            else -> { }
        }
    }
}

class CustomWebViewClient(
    private val context: Context,
    private val onPageStarted: () -> Unit,
    private val onPageFinished: () -> Unit,
    private val onUpdateCurrentUrl: (String) -> Unit,
    private val onReceiveError: () -> Unit
): WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageStarted()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinished()
    }

    override fun shouldOverrideUrlLoading(webView: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        return if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")) {
            onUpdateCurrentUrl(url)
            webView?.loadUrl(url)
            true
        } else {
            context.handleNonHttpUrls(url = url)
            true
        }
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        onReceiveError()
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        onReceiveError()
    }
}

class CustomWebChromeClient(
    private val context: Context,
    private val onAlertResult: (String, JsResult) -> Unit = { _, _ -> },
    private val onConfirmResult: (String, JsResult) -> Unit = { _, _ -> },
    private val onPromptResult: (String, String, JsPromptResult) -> Unit = { _, _, _ -> },
    private val onCustomViewShown: (View, CustomViewCallback) -> Unit = { _, _ -> },
    private val onCustomViewHidden: () -> Unit = { }
): WebChromeClient() {
    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        message?.let { onAlertResult(it, result!!) }
        return true
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        message?.let { onConfirmResult(it, result!!) }
        return true
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        message?.let { onPromptResult(it, defaultValue.toString(), result!!) }
        return true
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
}

private fun Context.handleNonHttpUrls(url: String) {
    when {
        url.startsWith("mailto:") -> openEmailFromUrl(url)
        url.startsWith("tel:") -> openDial(url)
        url.startsWith("sms:") -> openSMS(url)
        url.startsWith("market://") -> openPlayStore(packageName)
    }
}