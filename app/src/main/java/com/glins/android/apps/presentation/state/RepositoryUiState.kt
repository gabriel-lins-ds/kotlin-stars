package com.glins.android.apps.presentation.state

import com.glins.android.apps.domain.model.Repository

sealed interface RepositoryUiState {

    object Loading : RepositoryUiState

    data class Success(
        val repositories: List<Repository>,
        val isLoadingMore: Boolean = false
    ) : RepositoryUiState

    data class Error(
        val message: String
    ) : RepositoryUiState
}