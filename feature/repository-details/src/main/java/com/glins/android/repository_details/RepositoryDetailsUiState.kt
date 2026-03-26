package com.glins.android.repository_details

import com.glins.android.domain.error.DomainError
import com.glins.android.domain.model.Repository

sealed interface RepositoryDetailsUiState {

    object Loading : RepositoryDetailsUiState

    data class Success(
        val repository: Repository
    ) : RepositoryDetailsUiState

    data class Error(
        val error: DomainError
    ) : RepositoryDetailsUiState
}