package app.mynta.template.android.presentation.web

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebScreen() {
    val webViewManager = WebViewManager(LocalContext.current)

    AndroidView(
        modifier = Modifier,
        factory = {
            webViewManager.loadUrl("https://google.com").getView()
        }
    )
}