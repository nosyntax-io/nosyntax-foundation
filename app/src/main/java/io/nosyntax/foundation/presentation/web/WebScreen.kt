package io.nosyntax.foundation.presentation.web

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
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
import androidx.compose.ui.viewinterop.AndroidView
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.ChangeScreenOrientationComponent
import io.nosyntax.foundation.core.component.NoConnectionComponent
import io.nosyntax.foundation.core.component.SystemUIControllerComponent
import io.nosyntax.foundation.core.component.SystemUIState
import io.nosyntax.foundation.core.utility.Connectivity
import io.nosyntax.foundation.core.utility.Downloader
import io.nosyntax.foundation.core.utility.Utilities.findActivity
import io.nosyntax.foundation.core.utility.WebView
import io.nosyntax.foundation.core.utility.monetize.BannerAd
import io.nosyntax.foundation.core.utility.rememberSaveableWebViewState
import io.nosyntax.foundation.core.utility.rememberWebViewNavigator
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.main.MainActivity
import io.nosyntax.foundation.presentation.web.component.AlertDialogComponent
import io.nosyntax.foundation.presentation.web.component.ConfirmDialogComponent
import io.nosyntax.foundation.presentation.web.component.JsDialog
import io.nosyntax.foundation.presentation.web.component.LoadingIndicator
import io.nosyntax.foundation.presentation.web.component.PromptDialogComponent
import io.nosyntax.foundation.presentation.web.component.chromeClient
import io.nosyntax.foundation.presentation.web.component.webClient
import io.nosyntax.foundation.presentation.web.utility.JavaScriptInterface
import kotlinx.coroutines.CoroutineScope

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(
    appConfig: AppConfig,
    url: String,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val context = LocalContext.current

    val webKitConfig = appConfig.configuration.modules.webkit

    val systemUiState = remember { mutableStateOf(SystemUIState.SYSTEM_UI_VISIBLE) }
    var requestedOrientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) }

    val webViewState = rememberSaveableWebViewState()
    val navigator = rememberWebViewNavigator()

    var jsDialogInfo by rememberSaveable { mutableStateOf<Pair<JsDialog?, JsResult?>?>(null) }
    var customWebView by rememberSaveable { mutableStateOf<View?>(null) }
    var customWebViewCallback by rememberSaveable { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }
    var noConnectionState by rememberSaveable { mutableStateOf(false) }

    var totalLoadedPages by remember { mutableIntStateOf(0) }

    SystemUIControllerComponent(systemUiState = systemUiState)
    ChangeScreenOrientationComponent(orientation = requestedOrientation)

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
            captureBackPresses = !drawerState.isOpen,
            onCreated = { webView ->
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

                    if (webKitConfig.userAgent.android != "") {
                        settings.userAgentString = webKitConfig.userAgent.android
                    }

                    addJavascriptInterface(
                        JavaScriptInterface(
                            context = context,
                            coroutineScope = coroutineScope
                        ), "io"
                    )

                    setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
                        val fileName = URLUtil.guessFileName(url, contentDisposition, mimeType)
                        Downloader(context).downloadFile(
                            fileName = fileName,
                            url = url,
                            userAgent = userAgent,
                            mimeType = mimeType
                        )
                        Toast.makeText(context, "${context.getString(R.string.download_started)} $fileName", Toast.LENGTH_LONG).show()
                    }
                }
            },
            client = webClient(
                context = context,
                onPageLoaded = {
                    if (totalLoadedPages < 7) {
                        totalLoadedPages++
                    } else {
                        (context.findActivity() as MainActivity).showInterstitial()
                        totalLoadedPages = 1
                    }
                },
                onResourceLoaded = {
                    val resourceContainer =
                        """javascript:(function() { 
                        var node = document.createElement('style');
                        node.type = 'text/css';
                        node.innerHTML = '${webKitConfig.customCss}';
                        document.head.appendChild(node);
                    })()""".trimIndent()
                    navigator.loadUrl(resourceContainer)
                },
                onRequestInterrupted = {
                    noConnectionState = true
                }
            ),
            chromeClient = chromeClient(
                context = context,
                onJsDialog = { dialog, result ->
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
                }
            )
        )
        val ads = appConfig.configuration.monetization.ads
        BannerAd(enabled = ads.enabled && ads.bannerDisplay, modifier = Modifier
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
        val indicatorConfig = appConfig.configuration.components.loadingIndicator
        if (indicatorConfig.display) {
            LoadingIndicator(indicatorConfig = indicatorConfig)
        }
    }

    // TODO: Reset connection state when update navigate.
    if (noConnectionState) {
        NoConnectionComponent(onRetry = {
            if (Connectivity.getInstance().isOnline()) {
                noConnectionState = false
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

        when(dialog) {
            is JsDialog.Alert -> {
                AlertDialogComponent(
                    message = dialog.message,
                    onConfirm = confirmDialog
                )
            }
            is JsDialog.Confirm -> {
                ConfirmDialogComponent(
                    message = dialog.message,
                    onCancel = cancelDialog,
                    onConfirm = confirmDialog
                )
            }
            is JsDialog.Prompt -> {
                PromptDialogComponent(
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
            else -> { }
        }
    }
}