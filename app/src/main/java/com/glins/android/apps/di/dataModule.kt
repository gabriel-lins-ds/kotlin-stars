package com.glins.android.apps.di

import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.repository.KotlinStarsRepositoryImpl
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        KotlinStarsLocalDataSource(
            repositoryDao = get(),
            remoteKeysDao = get(),
            appDatabase = get()
        )
    }

    single<KotlinStarsRepository> {
        KotlinStarsRepositoryImpl(
            localDataSource = get(),
            api = get()
        )
    }
}