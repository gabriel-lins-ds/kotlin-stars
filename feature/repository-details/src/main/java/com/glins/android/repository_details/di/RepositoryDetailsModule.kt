package com.glins.android.repository_details.di

import com.glins.android.repository_details.RepositoryDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repositoryDetailsModule = module {
    viewModelOf(::RepositoryDetailsViewModel)
}