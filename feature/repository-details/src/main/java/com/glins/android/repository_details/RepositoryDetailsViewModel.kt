package com.glins.android.repository_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glins.android.domain.error.DomainError
import androidx.navigation.toRoute
import com.glins.android.common.routes.RepositoryDetailsRoute
import com.glins.android.common.navigator.BrowserNavigator
import com.glins.android.domain.usecase.GetRepositoryByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRepositoryByIdUseCase: GetRepositoryByIdUseCase,
    private val browserNavigator: BrowserNavigator
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
            getRepositoryByIdUseCase(repoId)
                .onSuccess { repo ->
                    _state.value = repo?.let {
                        RepositoryDetailsUiState.Success(it)
                    } ?: RepositoryDetailsUiState.Error(DomainError.NotFound)
                }.onFailure {
                    _state.value = RepositoryDetailsUiState.Error(DomainError.Unexpected)
                }
        }
    }

    fun onOpenUrlClick(url: String) {
        browserNavigator.openUrl(url)
    }
}
