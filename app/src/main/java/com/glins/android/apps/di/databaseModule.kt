package com.glins.android.apps.di

import androidx.room.Room
import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "repositories.db"
        ).build()
    }

    single<RepositoryDao> {
        get<AppDatabase>().repositoryDao()
    }
}