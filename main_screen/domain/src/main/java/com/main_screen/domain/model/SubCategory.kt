package com.main_screen.domain.model

data class SubCategory(
    val depth: Int,
    val description: String?,
    val icon: String,
    val slug: String,
    val subCategories: List<SubCategory>,
    val title: String,
    val webp_icon: String
)