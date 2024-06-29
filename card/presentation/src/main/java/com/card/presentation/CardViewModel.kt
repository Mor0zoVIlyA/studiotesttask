package com.card.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.card.domain.NONE
import com.card.domain.SharedModel
import com.card.domain.use_cases.FetchFullItemUseCase
import com.card.domain.use_cases.ShareUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = CardViewModel.ViewModelFactory::class)
class CardViewModel @AssistedInject constructor(
    @Assisted
    private val slug: String,
    private val fetchFullItemUseCase: FetchFullItemUseCase,
    private val shareUseCase: ShareUseCase
) : ViewModel() {
    private val infoFlow = MutableStateFlow(NONE)
    val infoState = infoFlow.asStateFlow()

    init {
        viewModelScope.launch {
            infoFlow.value = fetchFullItemUseCase.fetchData(slug)
        }
    }

    fun shareInfo(name: String, sku: String){
        try {
            shareUseCase.share(SharedModel(name, sku))

        }catch (e: Exception){
            Log.d("TAG", "shareInfo: $e")
        }
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(slug: String): CardViewModel
    }
}
