package com.second_layer_catalog.domain.use_case

import com.second_layer_catalog.domain.SecondLayerRepository
import javax.inject.Inject

class FetchGoodsNameUseCase @Inject constructor(
    private val secondLayerRepository: SecondLayerRepository
) {
    suspend fun fetchGoodsName(slug: String) = secondLayerRepository.fetchGoods(slug)
}