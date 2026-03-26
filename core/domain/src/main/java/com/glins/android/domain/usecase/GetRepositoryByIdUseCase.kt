package com.glins.android.domain.usecase

import com.glins.android.domain.model.Repository
import com.glins.android.domain.repository.KotlinStarsRepository

class GetRepositoryByIdUseCase(
    private val repository: KotlinStarsRepository
) {
    suspend operator fun invoke(id: Long): Result<Repository?> {
        return runCatching {
            repository.getRepositoryById(id)
        }
    }
}
