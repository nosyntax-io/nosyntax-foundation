package io.nosyntax.foundation.presentation.web.component

import android.webkit.URLUtil
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.nosyntax.foundation.core.utility.WebKitClient
import io.nosyntax.foundation.core.utility.handleIntent

@Composable
fun webClient(
    onPageLoaded: () -> Unit,
    onResourceLoaded: () -> Unit,
    onRequestInterrupted: () -> Unit
): WebKitClient {
    val context = LocalContext.current

    val webClient = remember {
        object: WebKitClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                onPageLoaded()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString().orEmpty()
                if (!URLUtil.isValidUrl(url)) {
                    context.handleIntent(url)
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