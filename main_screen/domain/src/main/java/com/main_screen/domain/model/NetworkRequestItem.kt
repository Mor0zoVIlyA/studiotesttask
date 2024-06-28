package com.main_screen.domain.model

import android.os.Parcel
import android.os.Parcelable

data class NetworkRequestItem(
    val depth: Int,
    val description: String?,
    val icon: String,
    val slug: String,
    val subCategories: List<SubCategory>,
    val title: String,
    val webp_icon: String
)