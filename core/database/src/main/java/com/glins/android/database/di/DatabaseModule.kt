package com.glins.android.database.di

import androidx.room.Room
import com.glins.android.database.AppDatabase
import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val DATABASE_NAME = "repositories.db"
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<AppDatabase>().repositoryDao() }

    single { get<AppDatabase>().remoteKeysDao() }

    singleOf(::KotlinStarsLocalDataSource)
}