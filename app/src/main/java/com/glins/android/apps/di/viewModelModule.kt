package com.glins.android.apps.di

import com.glins.android.apps.presentation.viewmodel.RepositoryDetailsViewModel
import com.glins.android.apps.presentation.viewmodel.RepositoryListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RepositoryListViewModel(
            repository = get()
        )
    }

    viewModel {
        RepositoryDetailsViewModel(
            savedStateHandle = get(),
            repository = get()
        )
    }
}