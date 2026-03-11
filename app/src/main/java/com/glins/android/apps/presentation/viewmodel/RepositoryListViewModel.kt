package com.glins.android.apps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.presentation.state.RepositoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val repository: KotlinStarsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RepositoryUiState> = MutableStateFlow(RepositoryUiState.Loading)
    val state: StateFlow<RepositoryUiState> = _state.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        viewModelScope.launch {
            _state.value = RepositoryUiState.Loading

            runCatching {
                repository.getTopRepositories(page = 1)
            }.onSuccess { repos ->
                currentPage = 1
                isLastPage = repos.isEmpty()

                _state.value =
                    RepositoryUiState.Success(repositories = repos)
            }.onFailure { error ->
                _state.value =
                    RepositoryUiState.Error(
                        message = error.message ?: "Unexpected error"
                    )
            }
        }
    }

    fun loadNextPage() {
        val currentState = _state.value

        if (currentState !is RepositoryUiState.Success) return
        if (currentState.isLoadingMore || isLastPage) return

        viewModelScope.launch {
            _state.value = currentState.copy(isLoadingMore = true)

            runCatching {
                repository.getTopRepositories(page = currentPage + 1)
            }.onSuccess { repos ->
                currentPage++

                if (repos.isEmpty()) {
                    isLastPage = true
                }

                _state.value =
                    currentState.copy(
                        repositories = currentState.repositories + repos,
                        isLoadingMore = false
                    )
            }.onFailure {
                _state.value = currentState.copy(isLoadingMore = false)
            }
        }
    }


    fun retry() {
        loadRepositories()
    }
}