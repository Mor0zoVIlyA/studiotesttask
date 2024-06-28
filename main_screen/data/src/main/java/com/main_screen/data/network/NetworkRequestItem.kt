package com.main_screen.data.network

data class NetworkRequestItem(
    val banner_href: String?,
    val banner_image: String?,
    val banner_mobile_image: String?,
    val depth: Int,
    val description: String,
    val icon: String,
    val seo_description: String,
    val seo_title: String,
    val slug: String,
    val subCategories: List<SubCategory>,
    val title: String,
    val webp_icon: String
)