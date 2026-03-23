package com.glins.android.apps.di

import com.glins.android.apps.ui.repositorydetails.RepositoryDetailsViewModel
import com.glins.android.apps.ui.repositorylist.RepositoryListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RepositoryListViewModel)
    viewModelOf(::RepositoryDetailsViewModel)
}