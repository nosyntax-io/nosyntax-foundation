package io.nosyntax.template.android.domain.model.app_config

data class WebKitConfig(
    val userAgent: UserAgent,
    val customCss: String) {

    data class UserAgent(
        val android: String
    )
}