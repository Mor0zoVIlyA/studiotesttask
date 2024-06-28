package com.second_layer_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.second_layer_catalog.data.network.models.Goods
import com.second_layer_catalog.domain.DomainGoods
import com.second_layer_catalog.domain.use_case.FetchGoodsNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = SecondScreenViewModel.ViewModelFactory::class)
class SecondScreenViewModel @AssistedInject constructor(
    @Assisted
    private val slug: String,
    private val fetchGoodsNameUseCase: FetchGoodsNameUseCase
): ViewModel() {
    private val listFlow = MutableStateFlow(emptyList<DomainGoods>())
    val listState = listFlow.asStateFlow()
    init {
        viewModelScope.launch {
            listFlow.value = fetchGoodsNameUseCase.fetchGoodsName(slug)
        }
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(slug: String): SecondScreenViewModel
    }
}