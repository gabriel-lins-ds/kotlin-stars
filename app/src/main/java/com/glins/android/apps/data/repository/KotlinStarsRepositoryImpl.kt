package com.glins.android.apps.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.domain.mapper.toDomain
import com.glins.android.apps.data.paging.RepositoryPagerFactory
import com.glins.android.apps.data.remote.api.KotlinStarsApi
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KotlinStarsRepositoryImpl(
    private val localDataSource: KotlinStarsLocalDataSource,
    private val api: KotlinStarsApi
) : KotlinStarsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRepositories(): Flow<PagingData<Repository>> {
        return RepositoryPagerFactory(localDataSource, api)
            .create()
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun getRepositoryById(id: Long): Repository? {
        return localDataSource.getRepositoryById(id)?.toDomain()
    }
}