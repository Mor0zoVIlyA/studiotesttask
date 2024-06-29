package com.card.domain.use_cases

import com.card.domain.CardRepository
import com.card.domain.SharedModel
import javax.inject.Inject

class ShareUseCase @Inject constructor(
    private val repository: CardRepository
) {
    fun share(sharedModel: SharedModel) = repository.share(sharedModel)
}