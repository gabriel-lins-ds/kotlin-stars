package com.glins.android.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.glins.android.database.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories ORDER BY `index` ASC")
    fun getRepositories(): PagingSource<Int, RepositoryEntity>

    @Query("SELECT * FROM repositories WHERE id = :id LIMIT 1")
    suspend fun getRepositoryById(id: Long): RepositoryEntity?

    @Upsert
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT MAX(lastUpdated) FROM repositories")
    suspend fun getLastUpdated(): Long?

    @Query("DELETE FROM repositories")
    suspend fun clear()
}