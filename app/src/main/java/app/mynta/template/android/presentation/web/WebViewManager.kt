package app.mynta.template.android.presentation.web

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewManager(context: Context) {
    @SuppressLint("SetJavaScriptEnabled")
    private val webView = WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = CustomWebViewClient()
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