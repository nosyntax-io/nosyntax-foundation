package app.mynta.template.android.presentation.navigation.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.mynta.template.android.R
import app.mynta.template.android.domain.model.NavigationItem

@Composable
fun coreNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            id = "privacy_policy",
            role = "policies",
            label = stringResource(id = R.string.privacy_policy),
            icon = "https://img.icons8.com/?size=512&id=78597&format=png"
        ),
        NavigationItem(
            id = "terms_of_use",
            role = "policies",
            label = stringResource(id = R.string.terms_of_use),
            icon = "https://img.icons8.com/?size=512&id=78597&format=png"
        ),
        NavigationItem(
            id = "divider",
            role = "divider"
        ),
        NavigationItem(
            id = "about",
            role = "about",
            label = stringResource(id = R.string.about),
            icon = "https://img.icons8.com/?size=512&id=78597&format=png"
        )
    )
}