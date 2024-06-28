package com.main_screen.domain

import com.main_screen.domain.model.NetworkRequestItem

interface RemoteRepository {
    suspend fun fetchAllData(): ArrayList<NetworkRequestItem>
}