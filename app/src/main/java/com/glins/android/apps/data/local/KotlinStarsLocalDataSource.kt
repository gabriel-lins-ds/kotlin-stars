package com.glins.android.apps.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.glins.android.apps.data.local.dao.RemoteKeysDao
import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.data.local.entity.RepositoryEntity

class KotlinStarsLocalDataSource(
    private val repositoryDao: RepositoryDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase
) {
    suspend fun getRepositoryById(id: Long): RepositoryEntity {
        return repositoryDao.getRepositoryById(id)
    }

    suspend fun getRemoteKeysRepoId(id: Long): RemoteKeys? {
        return remoteKeysDao.getRemoteKeysRepoId(id)
    }

    fun getRepositories(): PagingSource<Int, RepositoryEntity> {
        return repositoryDao.getRepositories()
    }

    suspend fun saveRepositories(
        repos: List<RepositoryEntity>,
        page: Int,
        isRefresh: Boolean,
        hasReachedEndOfPagination: Boolean
    ) {
        appDatabase.withTransaction {
            if (isRefresh) {
                remoteKeysDao.clearRemoteKeys()
                repositoryDao.clear()
            }

            val keys = repos.map {
                RemoteKeys(
                    repoId = it.id,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (hasReachedEndOfPagination) null else page + 1
                )
            }

            remoteKeysDao.insertAll(keys)

            repositoryDao.insertRepositories(repos)
        }
    }

    suspend fun getLastUpdated(): Long? {
        return repositoryDao.getLastUpdated()
    }
}