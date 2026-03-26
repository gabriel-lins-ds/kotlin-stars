package com.glins.android.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import com.glins.android.database.entity.RepositoryEntity
import com.glins.android.network.api.KotlinStarsApi
import com.glins.android.domain.error.DomainException
import com.glins.android.data.mapper.toDomainError
import com.glins.android.data.mapper.toEntity
import com.glins.android.data.constants.DataConstants.CACHE_TIMEOUT
import com.glins.android.database.entity.RemoteKeys

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val api: KotlinStarsApi,
    private val localDataSource: KotlinStarsLocalDataSource
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = localDataSource.getLastUpdated() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val isCacheValid = (System.currentTimeMillis() - lastUpdated) < CACHE_TIMEOUT

        return if (isCacheValid) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = api.searchKotlinRepositories(
                page = page,
                perPage = state.config.pageSize
            )

            val repos = response.items
            val endOfPaginationReached = repos.isEmpty() || repos.size < state.config.pageSize
            val entities = repos.toEntity(page = page)

            localDataSource.saveRepositories(
                repos = entities,
                page = page,
                isRefresh = loadType == LoadType.REFRESH,
                hasReachedEndOfPagination = endOfPaginationReached
            )

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (t: Throwable) {
            val error = t.toDomainError()
            return MediatorResult.Error(DomainException(error))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepositoryEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            localDataSource.getRemoteKeysRepoId(repo.id)
        }
    }
}
