package io.nosyntax.foundation.core.utility

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.domain.model.app_config.Settings
import io.nosyntax.foundation.domain.model.app_config.Monetization
import io.nosyntax.foundation.domain.model.app_config.Theme

@Preview(name = "Light Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_YES)
annotation class Previews

@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
annotation class ThemePreviews

class AppConfigProvider: PreviewParameterProvider<AppConfig> {
    override val values = sequenceOf(
        AppConfig(
            id = "com.example.app",
            name = "Example",
            version = "1.0",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            settings = Settings(
                entryPage = "1",
                email = "email@example.com",
                privacyPolicy = "https://legal.com/privacy",
                termsOfService = "https://legal.com/terms"
            ),
            theme = Theme(
                colorScheme = Theme.ColorScheme(
                    primary = "#369770",
                    onPrimary = "#FFFFFF",
                    secondary = "#1d6af5",
                    onSecondary = "#FFFFFF",
                    backgroundLight = "#fcfcfc",
                    onBackgroundLight = "#19191a",
                    surfaceLight = "#f4f4f4",
                    onSurfaceLight = "#19191a",
                    outlineLight = "#f4f4f4",
                    outlineVariantLight = "#121212",
                    backgroundDark = "#181A20",
                    onBackgroundDark = "#FFFFFF",
                    surfaceDark = "#21252F",
                    onSurfaceDark = "#F0F0F0",
                    outlineDark = "#2C2F36",
                    outlineVariantDark = "#121212"
                ),
                typography = Theme.Typography(
                    primaryFontFamily = "Poppins",
                    secondaryFontFamily = "Poppins"
                ),
                darkMode = true
            ),
            components = Components(
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
                            route = "web-000",
                            type = "page",
                            label = "Home",
                            icon = "https://img.icons8.com/?size=256&id=gE0woMnZGtua&format=png",
                            action = "https://home.com"
                        ),
                        NavigationItem(
                            route = "web-2",
                            type = "page",
                            label = "Store",
                            icon = "https://img.icons8.com/?size=100&id=dosi6bTgHIrs&format=png",
                            action = "https://store.com"
                        ),
                        NavigationItem(
                            route = "web-3",
                            type = "page",
                            label = "News",
                            icon = "https://img.icons8.com/?size=256&id=D9RFWfYE57lW&format=png",
                            action = "https://news.com"
                        ),
                        NavigationItem(
                            route = "mail-4",
                            type = "page",
                            label = "Contact Us",
                            icon = "https://img.icons8.com/fluency-systems-filled/96/new-post.png",
                            action = "mailto:email@example.com"
                        ),
                        NavigationItem(
                            route = "dial-5",
                            type = "page",
                            label = "Call Us",
                            icon = "https://img.icons8.com/fluency-systems-filled/96/phone-disconnected.png",
                            action = "tel:+0123456789"
                        ),
                        NavigationItem(
                            route = "sms-6",
                            type = "page",
                            label = "Send SMS",
                            icon = "https://img.icons8.com/fluency-systems-filled/96/speech-bubble-with-dots.png",
                            action = "sms:+0123456789"
                        ),
                        NavigationItem(
                            route = "settings-7",
                            type = "page",
                            label = "Settings",
                            icon = "https://img.icons8.com/fluency-systems-filled/100/settings.png"
                        ),
                        NavigationItem(
                            route = "about-8",
                            type = "page",
                            label = "About Us",
                            icon = "https://img.icons8.com/fluency-systems-filled/100/user-male-circle.png"
                        )
                    )
                ),
                navigationBar = Components.NavigationBar(
                    visible = true,
                    background = "neutral",
                    label = "visible",
                    items = listOf(
                        NavigationItem(
                            route = "web-000",
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
            ),
            monetization = Monetization(
                ads = Monetization.Ads(
                    enabled = true,
                    bannerDisplay = true,
                    interstitialDisplay = true
                )
            )
        )
    )
}