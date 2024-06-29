package com.card.domain

import androidx.appcompat.view.menu.ListMenuPresenter

data class DomainGoodInfo(
    val title: String,
    val slug: String,
    val price: Int,
    val priceOld: Int,
    val sku: String,
    val units: String,
    val imageUrlList: List<String>,
    val salePercentage: Int = if (priceOld == 0) 0 else ((priceOld - price).toFloat() / priceOld * 100).toInt(),
)

val NONE = DomainGoodInfo("", "", 0, 0, "", "", emptyList())