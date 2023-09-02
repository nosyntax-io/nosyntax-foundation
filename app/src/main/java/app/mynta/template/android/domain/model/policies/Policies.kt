package app.mynta.template.android.domain.model.policies

data class Policies(
    val privacy: Privacy,
    val terms: Terms,
    val response: Response) {

    data class Privacy(
        val content: String
    )

    data class Terms(
        val content: String
    )

    data class Response(
        val code: Int
    )
}