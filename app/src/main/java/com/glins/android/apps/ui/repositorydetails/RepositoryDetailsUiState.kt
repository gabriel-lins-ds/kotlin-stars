package com.glins.android.apps.ui.repositorydetails

import com.glins.android.apps.domain.error.DomainError
import com.glins.android.apps.domain.model.Repository

sealed interface RepositoryDetailsUiState {

    object Loading : RepositoryDetailsUiState

    data class Success(
        val repository: Repository
    ) : RepositoryDetailsUiState

    data class Error(
        val error: DomainError
    ) : RepositoryDetailsUiState
}