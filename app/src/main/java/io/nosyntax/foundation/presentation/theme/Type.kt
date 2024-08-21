package io.nosyntax.foundation.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
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

fun Typography.resolveTypography(
    primaryFont: FontFamily,
    secondaryFont: FontFamily
) = Typography(
    displayLarge = displayLarge.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    displayMedium = displayMedium.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    displaySmall = displaySmall.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineLarge = headlineLarge.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineMedium = headlineMedium.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineSmall = headlineSmall.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    titleLarge = titleLarge.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    titleMedium = titleMedium.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    titleSmall = titleSmall.copy(
        fontFamily = primaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    bodyLarge = bodyLarge.copy(
        fontFamily = secondaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    bodyMedium = bodyMedium.copy(
        fontFamily = secondaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    bodySmall = bodySmall.copy(
        fontFamily = secondaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    labelLarge = labelLarge.copy(
        fontFamily = secondaryFont,
        platformStyle = PlatformTextStyle(false)
    ),
    labelMedium = labelMedium.copy(
        fontFamily = secondaryFont,
        lineHeight = 20.0.sp,
        platformStyle = PlatformTextStyle(false)
    ),
    labelSmall = labelSmall.copy(
        fontFamily = secondaryFont,
        platformStyle = PlatformTextStyle(false)
    )
)