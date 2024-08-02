package io.nosyntax.foundation.core.utility

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.nosyntax.foundation.domain.model.app_config.AppBarConfig
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.ColorSchemeConfig
import io.nosyntax.foundation.domain.model.app_config.ComponentsConfig
import io.nosyntax.foundation.domain.model.app_config.LoadingIndicatorConfig
import io.nosyntax.foundation.domain.model.app_config.MonetizationConfig
import io.nosyntax.foundation.domain.model.app_config.SideMenuConfig
import io.nosyntax.foundation.domain.model.app_config.ThemeConfig
import io.nosyntax.foundation.domain.model.app_config.TypographyConfig

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
            app = AppConfig.App(
                id = "io.nosyntax.foundation.android",
                name = "NoSyntax",
                category = "Utility",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                theme = ThemeConfig(
                    colorScheme = ColorSchemeConfig(
                        primary = "#369770",
                        onPrimary = "#FFFFFF",
                        secondary = "#1d6af5",
                        onSecondary = "#FFFFFF",
                        backgroundLight = "#fcfcfc",
                        onBackgroundLight = "#19191a",
                        surfaceLight = "#f4f4f4",
                        onSurfaceLight = "#19191a",
                        outlineLight = "#f4f4f4",
                        backgroundDark = "#181A20",
                        onBackgroundDark = "#FFFFFF",
                        surfaceDark = "#21252F",
                        onSurfaceDark = "#F0F0F0",
                        outlineDark = "#2C2F36"
                    ),
                    typography = TypographyConfig(
                        primaryFontFamily = "Poppins",
                        secondaryFontFamily = "Poppins"
                    ),
                    settings = ThemeConfig.SettingsConfig(
                        darkMode = true
                    )
                ),
                components = ComponentsConfig(
                    appBar = AppBarConfig(
                        visible = true,
                        background = "solid",
                        title = AppBarConfig.Title(
                            visible = true,
                            alignment = "start"
                        )
                    ),
                    sideMenu = SideMenuConfig(
                        visible = true,
                        background = "neutral",
                        header = SideMenuConfig.Header(
                            visible = true,
                            image = "https://nosyntax.io/img.php"
                        ),
                        items = listOf(
                            SideMenuConfig.Item(
                                route = "web-1",
                                label = "Home",
                                icon = "https://img.icons8.com/?size=256&id=gE0woMnZGtua&format=png",
                                action = "https://google.com"
                            ),
                            SideMenuConfig.Item(
                                route = "web-2",
                                label = "Store",
                                icon = "https://img.icons8.com/?size=100&id=dosi6bTgHIrs&format=png",
                                action = "https://medium.com"
                            ),
                            SideMenuConfig.Item(
                                route = "web-3",
                                label = "News",
                                icon = "https://img.icons8.com/?size=256&id=D9RFWfYE57lW&format=png",
                                action = "https://instagram.com"
                            ),
                            SideMenuConfig.Item(
                                route = "mail-4",
                                label = "Email Us",
                                icon = "https://img.icons8.com/fluency-systems-filled/96/new-post.png",
                                action = "mailto:rixhion@gmail.com"
                            ),
                            SideMenuConfig.Item(
                                route = "dial-5",
                                label = "Call Us",
                                icon = "https://img.icons8.com/fluency-systems-filled/96/phone-disconnected.png",
                                action = "tel:+201550281644"
                            ),
                            SideMenuConfig.Item(
                                route = "sms-5",
                                label = "Send Us SMS",
                                icon = "https://img.icons8.com/fluency-systems-filled/96/speech-bubble-with-dots.png",
                                action = "sms:+201550281644"
                            ),
                            SideMenuConfig.Item(
                                route = "settings-4",
                                label = "Settings",
                                icon = "https://img.icons8.com/fluency-systems-filled/100/settings.png"
                            ),
                            SideMenuConfig.Item(
                                route = "about-5",
                                label = "About Us",
                                icon = "https://img.icons8.com/fluency-systems-filled/100/user-male-circle.png"
                            )
                        )
                    ),
                    loadingIndicator = LoadingIndicatorConfig(
                        display = true,
                        animation = "loading_indicator_9",
                        background = "solid",
                        color = "neutral"
                    )
                ),
                configuration = AppConfig.Configuration(
                    monetization = MonetizationConfig(
                        ads = MonetizationConfig.Ads(
                            enabled = true,
                            bannerDisplay = true,
                            interstitialDisplay = true
                        )
                    )
                )
            )
        )
    )
}