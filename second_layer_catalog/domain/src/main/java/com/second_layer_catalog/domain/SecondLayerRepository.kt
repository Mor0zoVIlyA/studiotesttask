package com.second_layer_catalog.domain

interface SecondLayerRepository {
    suspend fun fetchGoods(slug: String): List<DomainGoods>
}