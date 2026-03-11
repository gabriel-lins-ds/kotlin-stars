package com.glins.android.apps.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.entity.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
}