package app.mynta.template.android.presentation.policies

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.component.CircularProgressBar
import app.mynta.template.android.core.component.NoConnectionComponent
import app.mynta.template.android.core.utility.Utilities.findActivity
import app.mynta.template.android.presentation.main.MainActivity
import app.mynta.template.android.ui.theme.DynamicTheme

@Composable
fun PoliciesScreen(
    viewModel: PoliciesViewModel = hiltViewModel(),
    navController: NavHostController,
    request: String
) {
    val context = LocalContext.current
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

    BackHandler(enabled = true) {
        (context.findActivity() as MainActivity).showInterstitial(onAdDismissed = {
            navController.popBackStack()
        })
    }
}

@Preview
@Composable
fun PoliciesScreenPreview() {
    DynamicTheme {
        PoliciesScreen(
            navController = rememberNavController(),
            request = "privacy_policy"
        )
    }
}