package com.second_layer_catalog.data.network.models

data class Purchase(
    val base_id: Int,
    val card_discount: Boolean,
    val count_available: Int,
    val price: Int,
    val price_old: Int,
    val product_slug: String,
    val size_discount: Double,
    val status_code: Int
)