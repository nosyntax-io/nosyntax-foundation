package io.nosyntax.foundation.core.utility

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "LTR", showBackground = true, locale = "en", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", group = "RTL", showBackground = true, locale = "ar", uiMode = UI_MODE_NIGHT_YES)
annotation class Previews

@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
annotation class ThemePreviews