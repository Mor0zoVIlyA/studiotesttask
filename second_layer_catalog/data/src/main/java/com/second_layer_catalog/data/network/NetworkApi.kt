package com.second_layer_catalog.data.network

import com.second_layer_catalog.data.network.models.Goods
import com.second_layer_catalog.data.network.models.GoodsItemSlug
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {
    @GET("/products/{categorySlug}/catalog")
    suspend fun getFullSlugs(
        @Path("categorySlug") slug: String,
    ): List<GoodsItemSlug>

    @GET("/products/{productSlug}")
    suspend fun getFullInfo(
        @Path("productSlug") slug: String,
    ): Goods
}