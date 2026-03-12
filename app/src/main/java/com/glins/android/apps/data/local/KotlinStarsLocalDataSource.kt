package com.glins.android.apps.data.local

import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.entity.RepositoryEntity

class KotlinStarsLocalDataSource(
    private val dao: RepositoryDao
) {
    suspend fun getRepositoryById(id: Long): RepositoryEntity {
        return dao.getRepositoryById(id)
    }
}