package app.mynta.template.android.presentation.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.mynta.template.android.R
import app.mynta.template.android.core.component.DynamicClickableIcon
import app.mynta.template.android.core.utility.Intents.openUrl
import app.mynta.template.android.core.utility.Utilities
import app.mynta.template.android.domain.model.app_config.AboutPageConfig
import app.mynta.template.android.ui.theme.DynamicTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutScreen(aboutPageConfig: AboutPageConfig) {
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(horizontal = 30.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier.alpha(.7f),
                text = aboutPageConfig.introduction,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            FlowRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                aboutPageConfig.socialLinks.forEach { socialLink ->
                    Box(modifier = Modifier.padding(5.dp)) {
                        SocialIcon(
                            imageUrl = socialLink.icon,
                            onClick = {
                                context.openUrl(url = socialLink.url)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                modifier = Modifier.alpha(.7f),
                text =
                    "${stringResource(R.string.copyright)} " +
                    "${Utilities.getCurrentYear()} " +
                    "${stringResource(R.string.app_name)}.\n" +
                    stringResource(R.string.all_rights_reserved),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SocialIcon(imageUrl: String, onClick: () -> Unit) {
    DynamicClickableIcon(
        modifier = Modifier.size(35.dp),
        source = imageUrl,
        tint = MaterialTheme.colorScheme.surfaceVariant,
        onClick = onClick
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    DynamicTheme(darkTheme = false) {
        val placeholderIcon = "https://img.icons8.com/?size=512&id=99291&format=png"
        val placeholderUrl = "https://example.com"
        val socialLinks = listOf(
            AboutPageConfig.SocialLink("Google", placeholderIcon, placeholderUrl),
            AboutPageConfig.SocialLink("Facebook", placeholderIcon, placeholderUrl),
            AboutPageConfig.SocialLink("Instagram", placeholderIcon, placeholderUrl),
        )

        AboutScreen(
            aboutPageConfig = AboutPageConfig(
                introduction = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                socialLinks = socialLinks
            )
        )
    }
}