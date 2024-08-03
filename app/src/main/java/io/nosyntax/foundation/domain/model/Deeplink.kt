package io.nosyntax.foundation.domain.model

import java.io.Serializable

data class Deeplink(
    val destination: String = "",
    val action: String = ""
): Serializable