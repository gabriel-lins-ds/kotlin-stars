package com.glins.android.apps.data.local.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.data.mapper.toEntity
import com.glins.android.apps.data.remote.api.KotlinStarsApi

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val api: KotlinStarsApi,
    private val localDataSource: KotlinStarsLocalDataSource
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1

            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(true)

                val remoteKeys = localDataSource.getRemoteKeysRepoId(lastItem.id)

                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(true)
            }
        }

        try {
            val response = api.searchKotlinRepositories(
                page = page,
                perPage = state.config.pageSize
            )

            val repos = response.items
            val endOfPaginationReached = repos.size < state.config.pageSize
            localDataSource.saveRepositories(
                repos = repos.map { it.toEntity() },
                page = page,
                isRefresh = loadType == LoadType.REFRESH,
                hasReachedEndOfPagination = endOfPaginationReached
            )

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}