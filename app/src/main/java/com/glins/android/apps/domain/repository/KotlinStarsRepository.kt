package com.glins.android.apps.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.glins.android.apps.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface KotlinStarsRepository {
    suspend fun getRepositoryById(id: Long): Repository
    @OptIn(ExperimentalPagingApi::class)
    fun getRepositories(): Flow<PagingData<Repository>>
}