package com.glins.android.apps.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.glins.android.apps.util.constants.NetworkConstants.CACHE_TIMEOUT
import com.glins.android.apps.domain.error.DomainException
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.domain.mapper.toEntity
import com.glins.android.apps.data.remote.api.KotlinStarsApi
import com.glins.android.apps.domain.mapper.toDomainError
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val api: KotlinStarsApi,
    private val localDataSource: KotlinStarsLocalDataSource
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = localDataSource.getLastUpdated() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = CACHE_TIMEOUT
        val isCacheValid = (System.currentTimeMillis() - lastUpdated) < cacheTimeout

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
        Log.d("Mediator", "loadType = $loadType")
        val page = when (loadType) {
            LoadType.REFRESH -> 1

            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(false)

                val remoteKeys = localDataSource.getRemoteKeysRepoId(lastItem.id)

                remoteKeys?.nextKey ?: run {
                    val lastPage = state.pages.size
                    lastPage + 1
                }
            }
        }

        try {
            val response = api.searchKotlinRepositories(
                page = page,
                perPage = state.config.pageSize
            )

            val repos = response.items
            val endOfPaginationReached = repos.size < state.config.pageSize
            val entities = repos.toEntity(page = page)
            val isRefresh = loadType == LoadType.REFRESH
            localDataSource.saveRepositories(
                repos = entities,
                page = page,
                isRefresh = isRefresh,
                hasReachedEndOfPagination = endOfPaginationReached
            )

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (t: Throwable) {
            return MediatorResult.Error(DomainException(t.toDomainError()))
        }
    }
}