package app.mynta.template.android.domain.model

data class JsDialogState(
    val type: String,
    val message: String,
    val defaultValue: String = ""
)