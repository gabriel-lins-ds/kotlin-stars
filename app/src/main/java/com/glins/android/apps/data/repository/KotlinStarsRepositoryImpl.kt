package com.glins.android.apps.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.glins.android.apps.core.constants.NetworkConstants.NETWORK_PAGE_SIZE
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.local.mediator.RepositoriesRemoteMediator
import com.glins.android.apps.data.mapper.toDomain
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

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RepositoriesRemoteMediator(
                api,
                localDataSource
            ),
            pagingSourceFactory = {
                localDataSource.getRepositories()
            }
        ).flow.map { pagingData ->
            pagingData. map { it.toDomain() }
        }
    }

    override suspend fun getRepositoryById(id: Long): Repository {
        return localDataSource.getRepositoryById(id)
            .toDomain()
    }
}