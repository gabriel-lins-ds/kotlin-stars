package com.glins.android.domain.di

import com.glins.android.domain.usecase.GetRepositoriesUseCase
import com.glins.android.domain.usecase.GetRepositoryByIdUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRepositoriesUseCase(get()) }
    factory { GetRepositoryByIdUseCase(get()) }
}
