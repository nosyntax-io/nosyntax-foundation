package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class TypographyConfigDto(
    @SerializedName("heading_typeface")
    val headingTypeface: String,
    @SerializedName("body_typeface")
    val bodyTypeface: String
)
