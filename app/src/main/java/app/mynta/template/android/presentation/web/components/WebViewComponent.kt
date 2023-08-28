package app.mynta.template.android.presentation.web.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.MailTo
import android.view.ViewGroup
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Intents.openDial
import app.mynta.template.android.core.utility.Intents.openEmail
import app.mynta.template.android.core.utility.Intents.openPlayStore
import app.mynta.template.android.core.utility.Intents.openSMS
import app.mynta.template.android.presentation.web.JsDialogState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComponent(url: String) {
    val webView = remember { mutableStateOf<WebView?>(null) }
    val noConnectionComponentState = remember { mutableStateOf(false) }
    val jsDialogState = remember { mutableStateOf<JsDialogState?>(null) }
    val jsResult = remember { mutableStateOf<JsResult?>(null) }
    val jsPromptResult = remember { mutableStateOf<JsPromptResult?>(null) }

    jsDialogState.value?.let { dialog ->
        when (dialog.type) {
            "alert" -> {
                AlertDialogComponent(title = "Alert", message = dialog.message, onDismiss = {
                    jsDialogState.value = null
                    jsResult.apply {
                        value?.confirm()
                        value = null
                    }
                })
            }
            "confirm" -> {
                ConfirmDialogComponent(title = "Confirm", message = dialog.message, onCancel = {
                    jsDialogState.value = null
                    jsResult.apply {
                        value?.cancel()
                        value = null
                    }
                }, onConfirm = {
                    jsDialogState.value = null
                    jsResult.apply {
                        value?.confirm()
                        value = null
                    }
                })
            }
            "prompt" -> {
                PromptDialogComponent(message = dialog.message, defaultValue = dialog.defaultValue, onCancel = {
                    jsDialogState.value = null
                    jsPromptResult.apply {
                        value?.cancel()
                        value = null
                    }
                }, onConfirm = { result ->
                    jsDialogState.value = null
                    jsPromptResult.apply {
                        value?.confirm(result)
                        value = null
                    }
                })
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                            noConnectionComponentState.value = true
                        }
                    }

                    webChromeClient = object: WebChromeClient() {
                        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                            jsDialogState.value = JsDialogState(type = "alert", message = message.toString())
                            jsResult.value = result
                            return true
                        }

                        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                            jsDialogState.value = JsDialogState(type = "confirm", message = message.toString())
                            jsResult.value = result
                            return true
                        }

                        override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                            jsDialogState.value = JsDialogState(type = "prompt", message = message.toString(), defaultValue = defaultValue.toString())
                            jsPromptResult.value = result
                            return true
                        }
                    }

                    loadUrl(url)
                    webView.value = this
                }
            }
        )
        if (noConnectionComponentState.value) {
            NoConnectionComponent(onRetry = {
                if (Connectivity.getInstance().isOnline()) {
                    noConnectionComponentState.value = false
                    webView.value?.reload()
                }
            })
        }
    }
}