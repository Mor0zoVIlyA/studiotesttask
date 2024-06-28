package com.main_screen.data

import com.main_screen.data.network.NetworkRequest
import retrofit2.http.GET

interface ServiceApi {
    @GET("categories/-/catalog")
    suspend fun getFullInfo(): NetworkRequest
}