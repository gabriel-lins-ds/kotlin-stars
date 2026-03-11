package com.glins.android.apps.presentation.state

sealed interface RepositoriesUiState {

    object Loading : RepositoriesUiState

    data class Success(
        val repositories: List<Repository>
    ) : RepositoriesUiState

    data class Error(
        val message: String
    ) : RepositoriesUiState
}