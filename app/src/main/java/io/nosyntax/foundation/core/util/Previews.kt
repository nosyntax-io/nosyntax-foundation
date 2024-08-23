package io.nosyntax.foundation.core.util

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.domain.model.app_config.Settings
import io.nosyntax.foundation.domain.model.app_config.Monetization
import io.nosyntax.foundation.domain.model.app_config.Theme
import io.nosyntax.foundation.domain.model.app_config.WebViewSettings

@Preview(name = "Light Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_YES)
annotation class Previews

@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
annotation class ThemePreviews

class AppConfigProvider : PreviewParameterProvider<AppConfig> {
    override val values = sequenceOf(
        AppConfig(
            id = "com.example.app",
            name = "Example",
            version = "1.0",
            description = "A simple app for previews.",
            settings = defaultSettings(),
            theme = defaultTheme(),
            components = defaultComponents(),
            webViewSettings = defaultWebSettings(),
            monetization = defaultMonetization()
        )
    )

    private fun defaultSettings() = Settings(
        entryPage = "1",
        email = "email@example.com",
        privacyPolicy = "https://legal.com/privacy",
        termsOfService = "https://legal.com/terms"
    )

    private fun defaultTheme() = Theme(
        colorScheme = Theme.ColorScheme(
            primary = Color(0xFF369770),
            onPrimary = Color(0xFFFFFFFF),
            secondary = Color(0xFF1d6af5),
            onSecondary = Color(0xFFFFFFFF),
            backgroundLight = Color(0xFFfcfcfc),
            onBackgroundLight = Color(0xFF19191a),
            surfaceLight = Color(0xFFf4f4f4),
            onSurfaceLight = Color(0xFF19191a),
            surfaceVariantLight = Color(0xFFf4f4f4),
            onSurfaceVariantLight = Color(0xFFf4f4f4),
            outlineLight = Color(0xFFf4f4f4),
            outlineVariantLight = Color(0xFF121212),
            backgroundDark = Color(0xFF181A20),
            onBackgroundDark = Color(0xFFFFFFFF),
            surfaceDark = Color(0xFF21252F),
            onSurfaceDark = Color(0xFFF0F0F0),
            surfaceVariantDark = Color(0xFFf4f4f4),
            onSurfaceVariantDark = Color(0xFFf4f4f4),
            outlineDark = Color(0xFF2C2F36),
            outlineVariantDark = Color(0xFF121212)
        ),
        typography = Theme.Typography(
            primaryFontFamily = "Poppins",
            secondaryFontFamily = "Poppins"
        ),
        darkMode = true
    )

    private fun defaultComponents() = Components(
        appBar = Components.AppBar(
            visible = true,
            background = "solid",
            title = Components.AppBar.Title(
                visible = true,
                alignment = "start"
            )
        ),
        navigationDrawer = Components.NavigationDrawer(
            visible = true,
            background = "neutral",
            header = Components.NavigationDrawer.Header(
                visible = true,
                image = "https://nosyntax.io/img.php"
            ),
            items = listOf(
                NavigationItem(
                    route = "route",
                    type = "page",
                    label = "Home",
                    icon = "https://img.icons8.com/?size=256&id=gE0woMnZGtua&format=png",
                    action = "https://home.com"
                )
            )
        ),
        navigationBar = Components.NavigationBar(
            visible = true,
            background = "neutral",
            label = "always",
            items = listOf(
                NavigationItem(
                    route = "route",
                    type = "page",
                    label = "Home",
                    icon = "https://img.icons8.com/?size=256&id=gE0woMnZGtua&format=png",
                    action = "https://home.com"
                )
            )
        ),
        loadingIndicator = Components.LoadingIndicator(
            visible = true,
            animation = "loading_indicator_9",
            background = "solid",
            color = "neutral"
        )
    )

    private fun defaultWebSettings() = WebViewSettings(
        javaScriptEnabled = true,
        cacheEnabled = true,
        geolocationEnabled = true,
        allowFileUploads = true,
        allowCameraAccess = true
    )

    private fun defaultMonetization() = Monetization(
        ads = Monetization.Ads(
            enabled = true,
            bannerDisplay = true,
            interstitialDisplay = true
        )
    )
}