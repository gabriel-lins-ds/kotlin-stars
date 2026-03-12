package com.glins.android.apps.data.local.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.data.mapper.toEntity
import com.glins.android.apps.data.remote.api.KotlinStarsApi

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val api: KotlinStarsApi,
    private val database: AppDatabase
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

                val remoteKeys = database.remoteKeysDao()
                    .remoteKeysRepoId(lastItem.id)

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
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.repositoryDao().clear()
                }

                val keys = repos.map {
                    RemoteKeys(
                        repoId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (repos.isEmpty()) null else page + 1
                    )
                }

                database.remoteKeysDao().insertAll(keys)

                database.repositoryDao().insertRepositories(
                    repos.map { it.toEntity() }
                )
            }

            return MediatorResult.Success(
                endOfPaginationReached = repos.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}