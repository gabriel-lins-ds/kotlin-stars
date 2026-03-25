package com.glins.android.data.di

import com.glins.android.data.repository.KotlinStarsRepositoryImpl
import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import com.glins.android.network.api.KotlinStarsApi
import com.glins.android.domain.repository.KotlinStarsRepository
import org.koin.dsl.module

val dataModule = module {
    single<KotlinStarsRepository> { 
        KotlinStarsRepositoryImpl(
            localDataSource = get<KotlinStarsLocalDataSource>(), 
            api = get<KotlinStarsApi>()
        ) 
    }
}
