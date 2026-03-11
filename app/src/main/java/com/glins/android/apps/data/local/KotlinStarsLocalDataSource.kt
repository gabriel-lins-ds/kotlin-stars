package com.glins.android.apps.data.local

import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.entity.RepositoryEntity

class KotlinStarsLocalDataSource(
    private val dao: RepositoryDao
) {

    suspend fun getRepositories(): List<RepositoryEntity> {
        return dao.getRepositories()
    }

    suspend fun saveRepositories(repositories: List<RepositoryEntity>) {
        dao.insertRepositories(repositories)
    }

    suspend fun clear() {
        dao.clear()
    }
}