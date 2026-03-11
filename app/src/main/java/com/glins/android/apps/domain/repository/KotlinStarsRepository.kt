package com.glins.android.apps.domain.repository

import com.glins.android.apps.domain.model.Repository

interface KotlinStarsRepository {
    suspend fun getTopRepositories(
        page: Int
    ): List<Repository>

    suspend fun getRepositoryById(id: Long): Repository
}