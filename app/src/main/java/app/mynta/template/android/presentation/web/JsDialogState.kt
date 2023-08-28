package app.mynta.template.android.presentation.web

data class JsDialogState(
    val type: String,
    val message: String,
    val defaultValue: String = ""
)