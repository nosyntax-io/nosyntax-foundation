package io.nosyntax.foundation.domain.model.app_config

data class Details(
    val id: String,
    val name: String,
    val version: String,
    val description: String,
    val email: String,
    val privacyPolicy: String
)