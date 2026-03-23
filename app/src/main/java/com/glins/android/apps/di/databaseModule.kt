package com.glins.android.apps.di

import androidx.room.Room
import com.glins.android.apps.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
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
}