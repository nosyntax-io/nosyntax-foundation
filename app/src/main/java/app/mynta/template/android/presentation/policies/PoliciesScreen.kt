package app.mynta.template.android.presentation.policies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mynta.template.android.core.components.CircularProgressBar
import app.mynta.template.android.core.components.NoConnectionComponent
import app.mynta.template.android.ui.theme.DynamicTheme

@Composable
fun PoliciesScreen(viewModel: PoliciesViewModel = hiltViewModel(), request: String) {
    val state by viewModel.policiesState.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(horizontal = 20.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp)
        ) {
            state.response?.let { policies ->
                val content = when (request) {
                    "privacy_policy" -> policies.privacy.content
                    else -> policies.terms.content
                }
                Text(
                    modifier = Modifier.alpha(.7f),
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (state.error != null) {
                NoConnectionComponent(onRetry = {
                    viewModel.requestPolicies()
                })
            }
            if (state.isLoading) {
                CircularProgressBar(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview
@Composable
fun PoliciesScreenPreview() {
    DynamicTheme {
        PoliciesScreen(
            request = "privacy_policy"
        )
    }
}