package io.nosyntax.foundation.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.AppConfigProvider
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.core.utility.Utilities
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun AboutScreen(appConfig: AppConfig) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = appConfig.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier.alpha(.8f),
                text =
                    "${stringResource(R.string.copyright)} " +
                    "${Utilities.getCurrentYear()} " +
                    "${stringResource(R.string.app_name)}.\n" +
                    stringResource(R.string.all_rights_reserved),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Previews
@Composable
fun AboutScreenPreview(
    @PreviewParameter(AppConfigProvider::class) appConfig: AppConfig
) {
    DynamicTheme {
        AboutScreen(appConfig = appConfig)
    }
}