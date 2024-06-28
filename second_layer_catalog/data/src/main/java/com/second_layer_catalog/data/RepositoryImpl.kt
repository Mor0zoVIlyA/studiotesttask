package com.second_layer_catalog.data

import com.second_layer_catalog.data.mapper.toDomain
import com.second_layer_catalog.data.network.NetworkApi
import com.second_layer_catalog.data.network.models.Goods
import com.second_layer_catalog.domain.DomainGoods
import com.second_layer_catalog.domain.SecondLayerRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.security.spec.ECField
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi
) : SecondLayerRepository {
    override suspend fun fetchGoods(slug: String): List<DomainGoods> = withContext(Dispatchers.IO) {
        try {
            val data = networkApi.getFullSlugs(slug)
            val deferredList = mutableListOf<Deferred<DomainGoods>>()
            data.onEach { itemSlug ->
                deferredList.add(
                    async {
                        networkApi.getFullInfo(itemSlug.slug).toDomain()
                    }
                )
            }
            return@withContext deferredList.awaitAll()
        }catch (e: Exception){
            if (e is CancellationException)
                throw e
        }
        return@withContext emptyList()
    }
}