package com.glins.android.apps.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glins.android.apps.data.local.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories ORDER BY stars DESC")
    fun getRepositories(): PagingSource<Int, RepositoryEntity>

    @Query("SELECT * FROM repositories WHERE id = :id LIMIT 1")
    suspend fun getRepositoryById(id: Long): RepositoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT MAX(lastUpdated) FROM repositories")
    suspend fun getLastUpdated(): Long?

    @Query("DELETE FROM repositories")
    suspend fun clear()
}