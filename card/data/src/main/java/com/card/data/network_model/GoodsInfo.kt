package com.card.data.network_model

data class GoodsInfo(
    val additionals: Additionals,
    val bonus: Double,
    val brand: String,
    val category_slug: String,
    val category_title: String,
    val description: String,
    val id: Int,
    val images: List<Image>,
    val measure: Measure,
    val order_days: String,
    val purchase: Purchase,
    val rating: Rating,
    val sku: Int,
    val slug: String,
    val title: String,
    val units: String,
    val use_other_description: Int,
    val weight: String
)