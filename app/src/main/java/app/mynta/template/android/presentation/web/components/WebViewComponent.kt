package app.mynta.template.android.presentation.web.components

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.MailTo
import android.view.View
import android.view.ViewGroup
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.components.ChangeScreenOrientationComponent
import app.mynta.template.android.core.components.SystemUIControllerComponent
import app.mynta.template.android.core.components.SystemUIState
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Intents.openDial
import app.mynta.template.android.core.utility.Intents.openEmail
import app.mynta.template.android.core.utility.Intents.openPlayStore
import app.mynta.template.android.core.utility.Intents.openSMS

data class JsDialogState(
    val type: String,
    val message: String,
    val defaultValue: String = ""
)

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComponent(modifier: Modifier = Modifier, url: String) {
    val systemUiState = remember { mutableStateOf(SystemUIState.SYSTEM_UI_VISIBLE) }
    var requestedOrientation by remember { mutableStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var noConnectionState by remember { mutableStateOf(false) }
    var jsDialogState by remember { mutableStateOf<JsDialogState?>(null) }
    var jsResult by remember { mutableStateOf<JsResult?>(null) }
    var jsPromptResult by remember { mutableStateOf<JsPromptResult?>(null) }
    var webCustomView by remember { mutableStateOf<View?>(null) }
    var webCustomViewCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }

    SystemUIControllerComponent(systemUiState = systemUiState)
    ChangeScreenOrientationComponent(orientation = requestedOrientation)

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
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
                    isVerticalScrollBarEnabled = false
                    isHorizontalScrollBarEnabled = false

                    webViewClient = object: WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            canGoBack = view?.canGoBack() ?: false
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }

                        override fun shouldOverrideUrlLoading(webView: WebView?, request: WebResourceRequest?): Boolean {
                            val webUrl = request?.url.toString()
                            webView?.let { view ->
                                if (webUrl.startsWith("http://") || webUrl.startsWith("https://") || webUrl.startsWith("file://")) {
                                    view.loadUrl(webUrl)
                                    return true
                                } else {
                                    when {
                                        webUrl.startsWith("mailto:") -> {
                                            val mail = MailTo.parse(webUrl)
                                            val recipient = mail.to
                                            val subject = mail.subject ?: ""
                                            val body = mail.body ?: ""
                                            context.openEmail(recipient = recipient, subject = subject, body = body)
                                        }
                                        webUrl.startsWith("tel:") -> {
                                            context.openDial(url = webUrl)
                                        }
                                        webUrl.startsWith("sms:") -> {
                                            context.openSMS(url = webUrl)
                                        }
                                        webUrl.startsWith("market://") -> {
                                            context.openPlayStore(packageName = context.packageName)
                                        }
                                    }
                                    return true
                                }
                            }
                            return false
                        }

                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                            super.onReceivedError(view, request, error)
                            noConnectionState = true
                        }
                    }

                    webChromeClient = object: WebChromeClient() {
                        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                            jsDialogState = JsDialogState(type = "alert", message = message.toString())
                            jsResult = result
                            return true
                        }

                        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                            jsDialogState = JsDialogState(type = "confirm", message = message.toString())
                            jsResult = result
                            return true
                        }

                        override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                            jsDialogState = JsDialogState(type = "prompt", message = message.toString(), defaultValue = defaultValue.toString())
                            jsPromptResult = result
                            return true
                        }

                        override fun getDefaultVideoPoster(): Bitmap? {
                            if (webCustomView == null) {
                                return null
                            }
                            return BitmapFactory.decodeResource(context.resources, 2130837573)
                        }

                        override fun onHideCustomView() {
                            if (webCustomView == null) {
                                return
                            }
                            systemUiState.value = SystemUIState.SYSTEM_UI_VISIBLE
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                            webCustomView = null
                            webCustomViewCallback?.onCustomViewHidden()
                            webCustomViewCallback = null
                        }

                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                            if (webCustomView != null) {
                                onHideCustomView()
                                return
                            }
                            webCustomView = view
                            webCustomViewCallback = callback

                            systemUiState.value = SystemUIState.SYSTEM_UI_HIDDEN
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }
                    }

                    loadUrl(url)
                    webView = this
                }
            }, update = {
                it.loadUrl(url)
            }
        )

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

        jsDialogState?.let { dialog ->
            when (dialog.type) {
                "alert" -> {
                    AlertDialogComponent(title = "Alert", message = dialog.message) {
                        jsDialogState = null
                        jsResult?.let {
                            it.confirm()
                            jsResult = null
                        }
                    }
                }
                "confirm" -> {
                    ConfirmDialogComponent(title = "Confirm", message = dialog.message, onCancel = {
                        jsDialogState = null
                        jsResult?.let {
                            it.cancel()
                            jsResult = null
                        }
                    }, onConfirm = {
                        jsDialogState = null
                        jsResult?.let {
                            it.confirm()
                            jsResult = null
                        }
                    })
                }
                "prompt" -> {
                    PromptDialogComponent(message = dialog.message, defaultValue = dialog.defaultValue, onCancel = {
                        jsDialogState = null
                        jsPromptResult?.let {
                            it.cancel()
                            jsPromptResult = null
                        }
                    }, onConfirm = { result ->
                        jsDialogState = null
                        jsPromptResult?.let {
                            it.confirm(result)
                            jsPromptResult = null
                        }
                    })
                }
            }
        }
    }

    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }
}