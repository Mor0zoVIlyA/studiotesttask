package com.main_screen.data.mapper

import com.main_screen.data.network.NetworkRequest
import com.main_screen.data.network.NetworkRequestItem
import com.main_screen.data.network.SubCategory


fun NetworkRequest.toDomain(hostUrl: String) = this.map{convertNetworkRequestItem(it, hostUrl)}

private fun convertNetworkRequestItem(oldItem: NetworkRequestItem, hostUrl: String): com.main_screen.domain.model.NetworkRequestItem {
    return com.main_screen.domain.model.NetworkRequestItem(
        depth = oldItem.depth,
        description = oldItem.description,
        icon = hostUrl + oldItem.icon,
        slug = oldItem.slug,
        subCategories = oldItem.subCategories.map { convertSubCategory(it, hostUrl) },
        title = oldItem.title,
        webp_icon = oldItem.webp_icon
    )
}

private fun convertSubCategory(oldSubCategory: SubCategory, hostUrl: String): com.main_screen.domain.model.SubCategory {
    return com.main_screen.domain.model.SubCategory(
        depth = oldSubCategory.depth,
        description = oldSubCategory.description,
        icon = hostUrl + oldSubCategory.icon,
        slug = oldSubCategory.slug,
        subCategories = oldSubCategory.subCategories.map { convertSubCategory(it, hostUrl) },
        title = oldSubCategory.title,
        webp_icon = oldSubCategory.webp_icon
    )
}