package io.nosyntax.foundation.presentation.web.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.nosyntax.foundation.core.utility.WebKitChromeClient
import io.nosyntax.foundation.presentation.web.utility.FileChooserDelegate

@Composable
fun chromeClient(
    context: Context,
    onJsDialogEvent: (JsDialog, JsResult) -> Unit,
    onCustomViewShown: (View, WebChromeClient.CustomViewCallback) -> Unit,
    onCustomViewHidden: () -> Unit,
    onGeolocationPrompt: (origin: String?, callback: GeolocationPermissions.Callback?) -> Unit
): WebKitChromeClient {
    var filePath by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    val fileChooserDelegate = FileChooserDelegate(context) { uris ->
        filePath?.onReceiveValue(uris)
        filePath = null
    }

    val fileChooser = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        fileChooserDelegate.onActivityResult(result.data)
    }

    val chromeClient = remember {
        object: WebKitChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Alert(message), result)
                    return true
                }
                result?.cancel()
                return false
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Confirm(message), result)
                    return true
                }
                result?.cancel()
                return false
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                if (message != null && result != null) {
                    onJsDialogEvent(JsDialog.Prompt(message, defaultValue.orEmpty()), result)
                    return true
                }
                result?.cancel()
                return false
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

            override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
                onGeolocationPrompt(origin, callback)
            }

            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams): Boolean {
                filePath?.onReceiveValue(null)
                filePath = filePathCallback

                val customFileChooserParams = object: FileChooserParams() {
                    override fun getMode() = 1
                    override fun getAcceptTypes() = arrayOf("image/*")
                    override fun isCaptureEnabled() = true
                    override fun getTitle() = fileChooserParams.title
                    override fun getFilenameHint() = fileChooserParams.filenameHint
                    override fun createIntent() = fileChooserParams.createIntent()
                }
                return fileChooserDelegate.onShowFileChooser(customFileChooserParams, fileChooser)
            }
        }
    }

    return chromeClient
}