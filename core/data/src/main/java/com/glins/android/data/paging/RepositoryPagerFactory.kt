package com.glins.android.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.glins.android.common.constants.CommonConstants.NETWORK_PAGE_SIZE
import com.glins.android.data.constants.DataConstants.NETWORK_PAGE_PREFETCH_DISTANCE
import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import com.glins.android.database.entity.RepositoryEntity
import com.glins.android.data.mediator.RepositoryRemoteMediator
import com.glins.android.network.api.KotlinStarsApi

@OptIn(ExperimentalPagingApi::class)
class RepositoryPagerFactory(
    private val localDataSource: KotlinStarsLocalDataSource,
    private val api: KotlinStarsApi
) {

    fun create(): Pager<Int, RepositoryEntity> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                prefetchDistance = NETWORK_PAGE_PREFETCH_DISTANCE,
                enablePlaceholders = true
            ),
            remoteMediator = RepositoryRemoteMediator(api, localDataSource),
            pagingSourceFactory = { localDataSource.getRepositories() }
        )
}
