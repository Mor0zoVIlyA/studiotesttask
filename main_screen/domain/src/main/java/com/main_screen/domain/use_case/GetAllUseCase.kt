package com.main_screen.domain.use_case

import com.main_screen.domain.RemoteRepository
import javax.inject.Inject

class GetAllUseCase @Inject constructor(
    private val repository: RemoteRepository,
) {
    suspend fun getAll() = repository.fetchAllData()
}