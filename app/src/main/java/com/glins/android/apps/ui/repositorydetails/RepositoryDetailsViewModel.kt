package com.glins.android.apps.ui.repositorydetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.ui.navigation.RepositoryDetailsRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: KotlinStarsRepository
) : ViewModel() {

    private val route = savedStateHandle.toRoute<RepositoryDetailsRoute>()
    private val repoId: Long = route.repoId

    private val _state =
        MutableStateFlow<RepositoryDetailsUiState>(RepositoryDetailsUiState.Loading)
    val state: StateFlow<RepositoryDetailsUiState> = _state.asStateFlow()

    init {
        loadRepository()
    }

    private fun loadRepository() {
        viewModelScope.launch {
            runCatching {
                repository.getRepositoryById(repoId)
            }.onSuccess { repo ->
                _state.value = repo?.let {
                    RepositoryDetailsUiState.Success(it)
                } ?: RepositoryDetailsUiState.Error("Repository not found")
            }.onFailure { error ->
                _state.value =
                    RepositoryDetailsUiState.Error(
                        error.message ?: "Unexpected error"
                    )
            }
        }
    }
}