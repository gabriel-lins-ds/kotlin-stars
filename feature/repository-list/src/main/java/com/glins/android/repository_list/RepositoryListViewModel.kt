package com.glins.android.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.glins.android.domain.usecase.GetRepositoriesUseCase

class RepositoryListViewModel(
    getRepositoriesUseCase: GetRepositoriesUseCase
) : ViewModel() {
    val repositories = getRepositoriesUseCase().cachedIn(viewModelScope)
}
