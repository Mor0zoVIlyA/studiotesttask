package com.card.domain.use_cases

import com.card.domain.CardRepository
import javax.inject.Inject

class FetchFullItemUseCase @Inject constructor(
    private val repository: CardRepository
) {
    suspend fun fetchData(slug: String) = repository.fetchFullItemInfo(slug)
}