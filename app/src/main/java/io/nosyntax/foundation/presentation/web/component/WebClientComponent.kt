package io.nosyntax.foundation.presentation.web.component

import android.content.Context
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.nosyntax.foundation.core.utility.Intents.handleUrlAction
import io.nosyntax.foundation.core.utility.Utilities
import io.nosyntax.foundation.core.utility.WebKitClient

@Composable
fun webClient(
    context: Context,
    onPageLoaded: () -> Unit,
    onResourceLoaded: () -> Unit,
    onRequestInterrupted: () -> Unit
): WebKitClient {
    val webClient = remember {
        object: WebKitClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                onPageLoaded()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                if (!Utilities.isUrlValid(url)) {
                    context.handleUrlAction(url)
                    return true
                }
                return false
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                onResourceLoaded()
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                val networkErrors = listOf(ERROR_CONNECT, ERROR_TIMEOUT, ERROR_HOST_LOOKUP, ERROR_UNKNOWN)
                if (request?.isForMainFrame == true && error?.errorCode in networkErrors) {
                    onRequestInterrupted()
                }
            }
        }
    }

    return webClient
}