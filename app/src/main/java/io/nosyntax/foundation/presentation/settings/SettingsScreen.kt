package io.nosyntax.foundation.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.AppConfigProvider
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.core.utility.openMailer
import io.nosyntax.foundation.core.utility.openPlayStore
import io.nosyntax.foundation.core.utility.openContent
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun SettingsScreen(appConfig: AppConfig, navigateToAbout: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        SettingItem(text = stringResource(id = R.string.rate_us)) {
            context.openPlayStore(context.packageName)
        }
        SettingDivider()
        SettingItem(text = stringResource(id = R.string.send_feedback)) {
            context.openMailer(arrayOf(appConfig.settings.email))
        }
        SettingDivider()
        SettingItem(text = stringResource(id = R.string.privacy_policy)) {
            context.openContent(appConfig.settings.privacyPolicy)
        }
        SettingDivider()
        SettingItem(text = stringResource(id = R.string.terms_of_service)) {
            context.openContent(appConfig.settings.termsOfService)
        }
        SettingDivider()
        SettingItem(text = stringResource(id = R.string.about_us)) {
            navigateToAbout()
        }
    }
}

@Composable
private fun SettingItem(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun SettingDivider() {
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Previews
@Composable
fun SettingsScreenPreview(
    @PreviewParameter(AppConfigProvider::class) appConfig: AppConfig
) {
    DynamicTheme {
        SettingsScreen(
            appConfig = appConfig,
            navigateToAbout = { }
        )
    }
}