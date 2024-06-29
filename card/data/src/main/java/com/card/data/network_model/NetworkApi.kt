package com.card.data.network_model

import android.accessibilityservice.GestureDescription.StrokeDescription
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {
    @GET("/products/{productSlug}")
    suspend fun getFullInfo(
        @Path("productSlug") slug: String
    ): GoodsInfo
}