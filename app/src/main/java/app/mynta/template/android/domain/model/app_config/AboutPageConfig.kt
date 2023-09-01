package app.mynta.template.android.domain.model.app_config

data class AboutPageConfig(
    val introduction: String,
    val socialLinks: List<SocialLink>) {

    data class SocialLink(
        val id: String,
        val label: String,
        val icon: String,
        val url: String
    )
}