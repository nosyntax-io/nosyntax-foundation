package io.nosyntax.foundation.domain.model.app_config

data class WebKitConfig(
    val userAgent: UserAgent) {

    data class UserAgent(
        val android: String
    )
}