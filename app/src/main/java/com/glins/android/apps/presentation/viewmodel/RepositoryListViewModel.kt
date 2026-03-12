package com.glins.android.apps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.glins.android.apps.domain.repository.KotlinStarsRepository

class RepositoryListViewModel(
    repository: KotlinStarsRepository
) : ViewModel() {
    val repositories = repository.getRepositories().cachedIn(viewModelScope)
}