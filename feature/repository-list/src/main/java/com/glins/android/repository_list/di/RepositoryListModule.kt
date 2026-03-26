package com.glins.android.repository_list.di

import com.glins.android.repository_list.RepositoryListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repositoryListModule = module {
    viewModelOf(::RepositoryListViewModel)
}