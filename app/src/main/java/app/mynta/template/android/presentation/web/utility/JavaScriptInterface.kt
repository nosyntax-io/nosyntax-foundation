package app.mynta.template.android.presentation.web.utility

import android.content.Context
import android.webkit.JavascriptInterface
import app.mynta.template.android.core.utility.Utilities.findActivity
import app.mynta.template.android.presentation.main.MainActivity
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