package app.mynta.template.android.presentation.web.component

import android.content.Context
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.mynta.template.android.core.utility.Intents.handleUrlAction
import app.mynta.template.android.core.utility.Utilities
import app.mynta.template.android.core.utility.WebKitClient

@Composable
fun webClient(
    context: Context,
    onResourceLoaded: () -> Unit,
    onRequestInterrupted: () -> Unit
): WebKitClient {
    val webClient = remember {
        object: WebKitClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                return if (Utilities.isUrlValid(url)) {
                    navigator.loadUrl(url)
                    true
                } else {
                    context.handleUrlAction(url)
                    true
                }
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                onResourceLoaded()
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                onRequestInterrupted()
            }
        }
    }

    return webClient
}