package app.mynta.template.android.presentation.web

import androidx.compose.runtime.Composable
import app.mynta.template.android.presentation.web.components.WebViewComponent

@Composable
fun WebScreen() {
    WebViewComponent(url = "https://google.com")
}