package com.glins.android.domain.usecase

import androidx.paging.PagingData
import com.glins.android.domain.model.Repository
import com.glins.android.domain.repository.KotlinStarsRepository
import kotlinx.coroutines.flow.Flow

class GetRepositoriesUseCase(
    private val repository: KotlinStarsRepository
) {
    operator fun invoke(): Flow<PagingData<Repository>> {
        return repository.getRepositories()
    }
}
