package io.nosyntax.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class TypographyConfigDto(
    @SerializedName("primary_font_family")
    val primaryFontFamily: String,
    @SerializedName("secondary_font_family")
    val secondaryFontFamily: String
)
