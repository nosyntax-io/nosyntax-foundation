package app.mynta.template.android.presentation.web

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import app.mynta.template.android.core.Constants

class WebViewManager(context: Context) {
    @SuppressLint("SetJavaScriptEnabled")
    private val webView = WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = CustomWebViewClient()

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
    }

    fun loadUrl(url: String): WebViewManager {
        webView.loadUrl(url)
        return this
    }

    fun getView(): WebView {
        return webView
    }

    private inner class CustomWebViewClient: WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }
}