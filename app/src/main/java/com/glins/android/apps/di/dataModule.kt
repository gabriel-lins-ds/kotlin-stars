package com.glins.android.apps.di

import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.repository.KotlinStarsRepositoryImpl
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::KotlinStarsLocalDataSource)
    singleOf(::KotlinStarsRepositoryImpl) { bind<KotlinStarsRepository>() }
}