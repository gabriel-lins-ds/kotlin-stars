package com.glins.android.data.di

import com.glins.android.data.repository.KotlinStarsRepositoryImpl
import com.glins.android.data.paging.RepositoryPagerFactory
import com.glins.android.domain.repository.KotlinStarsRepository
import org.koin.dsl.module

val dataModule = module {
    single { RepositoryPagerFactory(get(), get()) }

    single<KotlinStarsRepository> { 
        KotlinStarsRepositoryImpl(
            localDataSource = get(),
            pagerFactory = get()
        ) 
    }
}
