package app.mynta.template.android.data.mapper.envato

import app.mynta.template.android.data.source.remote.dto.envato.EnvatoAuthorSaleDto
import app.mynta.template.android.domain.model.envato.EnvatoAuthorSale

fun EnvatoAuthorSaleDto.toAuthorSale(): EnvatoAuthorSale {
    return EnvatoAuthorSale(
        license = license,
        amount = amount,
        soldAt = soldAt,
        buyer = buyer,
        item = EnvatoAuthorSale.Item(
            item.id,
            item.name
        )
    )
}