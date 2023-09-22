package app.mynta.template.android.presentation.web

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.FrameLayout
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
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.component.ChangeScreenOrientationComponent
import app.mynta.template.android.core.component.NoConnectionComponent
import app.mynta.template.android.core.component.SystemUIControllerComponent
import app.mynta.template.android.core.component.SystemUIState
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.Utilities.findActivity
import app.mynta.template.android.core.utility.WebView
import app.mynta.template.android.core.utility.monetize.BannerAd
import app.mynta.template.android.core.utility.rememberSaveableWebViewState
import app.mynta.template.android.core.utility.rememberWebViewNavigator
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.presentation.main.MainActivity
import app.mynta.template.android.presentation.web.component.AlertDialogComponent
import app.mynta.template.android.presentation.web.component.ConfirmDialogComponent
import app.mynta.template.android.presentation.web.component.JsDialog
import app.mynta.template.android.presentation.web.component.LoadingIndicator
import app.mynta.template.android.presentation.web.component.PromptDialogComponent
import app.mynta.template.android.presentation.web.component.chromeClient
import app.mynta.template.android.presentation.web.component.webClient
import app.mynta.template.android.presentation.web.utility.JavaScriptInterface
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

    val webKitConfig = appConfig.modules.webkit

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
                        ), "app"
                    )

                    setDownloadListener { url, _, _, _, _ ->
                        context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
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
        BannerAd(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
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
        val indicatorConfig = appConfig.appearance.components.loadingIndicator
        LoadingIndicator(indicatorConfig = indicatorConfig)
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