package io.nosyntax.foundation.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.core.utility.Utilities.setColorContrast
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun NoConnectionView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(130.dp),
            painter = painterResource(id = R.drawable.icon_wifi_outline),
            contentDescription = stringResource(id = R.string.no_internet_connection),
            colorFilter = ColorFilter.tint(color = setColorContrast(
                isDark = isSystemInDarkTheme(),
                color = MaterialTheme.colorScheme.surface
            ))
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.no_internet_connection),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = stringResource(id = R.string.no_internet_connection_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(0.dp),
            onClick = onRetry
        ) {
            Text(
                text = stringResource(id = R.string.try_again),
                modifier = Modifier.padding(horizontal = 30.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Previews
@Composable
fun NoConnectionPreview() {
    DynamicTheme {
        NoConnectionView { }
    }
}