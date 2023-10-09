package app.mynta.template.android.domain.model.app_config

data class AboutPageConfig(
    val introduction: String,
    val connectItems: List<ConnectItem>) {

    data class ConnectItem(
        val label: String,
        val icon: String,
        val url: String
    )
}