package com.glins.android.apps.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.presentation.state.RepositoryDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: KotlinStarsRepository
) : ViewModel() {

    private val repoId: Long =
        savedStateHandle["repoId"] ?: 0

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
                _state.value =
                    RepositoryDetailsUiState.Success(repo)
            }.onFailure { error ->
                _state.value =
                    RepositoryDetailsUiState.Error(
                        error.message ?: "Unexpected error"
                    )
            }
        }
    }
}