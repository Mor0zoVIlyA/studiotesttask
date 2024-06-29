package com.card.data.mapper

import com.card.data.network_model.GoodsInfo
import com.card.domain.DomainGoodInfo

fun GoodsInfo.toDomain(baseUrl: String) = DomainGoodInfo(
    title,
    slug,
    (purchase.price / 100),
    (purchase.price_old / 100),
    sku.toString(),
    units,
    images.map { baseUrl + it.original }
)