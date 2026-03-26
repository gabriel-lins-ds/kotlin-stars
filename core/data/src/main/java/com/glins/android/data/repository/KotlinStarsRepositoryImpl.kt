package com.glins.android.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import com.glins.android.data.paging.RepositoryPagerFactory
import com.glins.android.network.api.KotlinStarsApi
import com.glins.android.data.mapper.toDomain
import com.glins.android.domain.model.Repository
import com.glins.android.domain.repository.KotlinStarsRepository
import kotlinx.coroutines.flow.Flow

class KotlinStarsRepositoryImpl(
    private val localDataSource: KotlinStarsLocalDataSource,
    private val api: KotlinStarsApi
) : KotlinStarsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRepositories(): Flow<PagingData<Repository>> {
        return RepositoryPagerFactory(localDataSource, api)
            .create()
            .flow
            .toDomain()
    }

    override suspend fun getRepositoryById(id: Long): Repository? {
        return localDataSource.getRepositoryById(id)?.toDomain()
    }
}
