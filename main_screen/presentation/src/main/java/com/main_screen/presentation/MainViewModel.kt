package com.main_screen.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main_screen.domain.model.NetworkRequestItem
import com.main_screen.domain.use_case.GetAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getAllUseCase: GetAllUseCase
) : ViewModel() {
    private val allData = MutableStateFlow<ArrayList<NetworkRequestItem>>(ArrayList())
    val dataList = allData.asStateFlow()
    val expandedStates = mutableStateMapOf<String, Boolean>()
    init {
        viewModelScope.launch {
            val data = getAllUseCase.getAll()
            allData.emit(data)
        }
    }
    fun toggleExpanded(slug: String, depth: Int) {
        val itemKey = generateItemKey(slug, depth)
        val currentState = expandedStates[itemKey] ?: false
        expandedStates[itemKey] = !currentState
    }

    fun isExpanded(slug: String, depth: Int): Boolean {
        val itemKey = generateItemKey(slug, depth)
        return expandedStates[itemKey] ?: false
    }

    private fun generateItemKey(slug: String, depth: Int): String {
        return "${slug}_${depth}"
    }
}