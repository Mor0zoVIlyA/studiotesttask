package com.main_screen.data

import com.example.networks.Url
import com.main_screen.data.mapper.toDomain
import com.main_screen.domain.RemoteRepository
import com.main_screen.domain.model.NetworkRequestItem
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi,
    @Url
    private val url: String
): RemoteRepository {
    override suspend fun fetchAllData(): ArrayList<NetworkRequestItem> {
        try {
            val data = serviceApi.getFullInfo().toDomain(url).toCollection(ArrayList())
            return data
        }catch (e: Exception){
            if (e is CancellationException)
                throw e
        }
        return emptyList<NetworkRequestItem>().toCollection(ArrayList())
    }
}