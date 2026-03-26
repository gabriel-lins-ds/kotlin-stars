package com.glins.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glins.android.database.dao.RemoteKeysDao
import com.glins.android.database.dao.RepositoryDao
import com.glins.android.database.entity.RemoteKeys
import com.glins.android.database.entity.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class, RemoteKeys::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}