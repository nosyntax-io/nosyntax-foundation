package app.mynta.template.android.domain.model.envato

data class EnvatoAuthorSale(
    val license: String,
    val amount: String,
    val soldAt: String,
    val buyer: String,
    val item: Item) {

    data class Item(
        val id: Int,
        val name: String
    )
}