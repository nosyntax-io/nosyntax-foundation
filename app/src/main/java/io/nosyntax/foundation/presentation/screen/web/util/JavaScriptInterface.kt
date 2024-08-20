package io.nosyntax.foundation.presentation.screen.web.util

import android.content.Context
import android.webkit.JavascriptInterface
import io.nosyntax.foundation.core.util.Utilities.findActivity
import io.nosyntax.foundation.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JavaScriptInterface(
    private val context: Context,
    private val coroutineScope: CoroutineScope
) {
    @JavascriptInterface
    fun showInterstitial() {
        coroutineScope.launch(Dispatchers.Main) {
            (context.findActivity() as MainActivity).showInterstitial()
        }
    }
}