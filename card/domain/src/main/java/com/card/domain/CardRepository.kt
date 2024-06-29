package com.card.domain

interface CardRepository {
    suspend fun fetchFullItemInfo(slug: String): DomainGoodInfo
    fun share(sharedModel: SharedModel)
}