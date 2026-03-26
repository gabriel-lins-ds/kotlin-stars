package com.glins.android.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.glins.android.domain.repository.KotlinStarsRepository

class RepositoryListViewModel(
    repository: KotlinStarsRepository
) : ViewModel() {
    val repositories = repository.getRepositories().cachedIn(viewModelScope)
}