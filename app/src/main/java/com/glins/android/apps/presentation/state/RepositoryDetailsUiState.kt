package com.glins.android.apps.presentation.state

import com.glins.android.apps.domain.model.Repository

sealed interface RepositoryDetailsUiState {

    object Loading : RepositoryDetailsUiState

    data class Success(
        val repository: Repository
    ) : RepositoryDetailsUiState

    data class Error(
        val message: String
    ) : RepositoryDetailsUiState
}