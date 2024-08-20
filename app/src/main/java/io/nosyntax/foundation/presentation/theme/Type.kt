package io.nosyntax.foundation.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import io.nosyntax.foundation.R

val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

data class DynamicTypography(
    val primaryFontFamily: FontFamily = TemplateFont,
    val secondaryFontFamily: FontFamily = TemplateFont
)

val TemplateFont = FontFamily(
    Font(R.font.font_normal, FontWeight.Normal),
    Font(R.font.font_medium, FontWeight.Medium),
    Font(R.font.font_semi_bold, FontWeight.SemiBold)
)

val Typography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    displayMedium = Typography().displayMedium.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleLarge = Typography().titleLarge.copy(
        fontSize = 20.sp,
        fontFamily = TemplateFont,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = TemplateFont,
        lineHeight = 20.0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = TemplateFont,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
)