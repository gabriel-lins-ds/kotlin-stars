package com.glins.android.apps.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.data.remote.api.KotlinStarsApi
import com.glins.android.apps.data.remote.dto.RepositoryDto
import com.glins.android.apps.data.remote.dto.SearchRepositoriesResponseDto
import com.glins.android.apps.tests.fixtures.createPagingState
import com.glins.android.apps.tests.fixtures.createRepositoryEntity
import com.glins.android.data.constants.DataConstants.CACHE_TIMEOUT
import com.glins.android.data.mediator.RepositoryRemoteMediator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediatorTest {

    private val api: KotlinStarsApi = mockk()
    private val localDataSource: KotlinStarsLocalDataSource = mockk()

    private lateinit var mediator: RepositoryRemoteMediator

    @BeforeEach
    fun setup() {
        mediator = RepositoryRemoteMediator(
            api = api,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `should launch initial refresh when cache does not exist`() = runTest {
        // Given
        coEvery { localDataSource.getLastUpdated() } returns null

        // When
        val result = mediator.initialize()

        // Then
        assertEquals(
            RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH,
            result
        )
    }

    @Test
    fun `should skip initial refresh when cache is still valid`() = runTest {
        // Given
        val now = System.currentTimeMillis()
        coEvery { localDataSource.getLastUpdated() } returns now

        // When
        val result = mediator.initialize()

        // Then
        assertEquals(
            RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH,
            result
        )
    }

    @Test
    fun `should launch initial refresh when cache expired`() = runTest {
        // Given
        val expiredTime = System.currentTimeMillis() - CACHE_TIMEOUT - 1000
        coEvery { localDataSource.getLastUpdated() } returns expiredTime

        // When
        val result = mediator.initialize()

        // Then
        assertEquals(
            RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH,
            result
        )
    }

    @Test
    fun `should return end of pagination when load type is prepend`() = runTest {
        // Given
        val state = createPagingState<Int, RepositoryEntity>()

        // When
        val result = mediator.load(
            LoadType.PREPEND,
            state
        )

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `should request first page when load type is refresh`() = runTest {
        // Given
        val response = mockk<SearchRepositoriesResponseDto>()
        val repos = listOf(mockk<RepositoryDto>())

        every { response.items } returns repos

        coEvery {
            api.searchKotlinRepositories(
                page = 1,
                perPage = any()
            )
        } returns response

        coEvery {
            localDataSource.saveRepositories(
                repos = any(),
                page = any(),
                isRefresh = any(),
                hasReachedEndOfPagination = any()
            )
        } just Runs

        val state = createPagingState<Int, RepositoryEntity>()

        // When
        mediator.load(
            LoadType.REFRESH,
            state
        )

        // Then
        coVerify {
            api.searchKotlinRepositories(
                page = 1,
                perPage = state.config.pageSize
            )
        }
    }

    @Test
    fun `should return success when last item is null on append`() = runTest {
        // Given
        val state = createPagingState<Int, RepositoryEntity>()

        // When
        val result = mediator.load(
            LoadType.APPEND,
            state
        )

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun `should request next page when remote key exists`() = runTest {
        // Given
        val lastItem = createRepositoryEntity()

        val remoteKeys = RemoteKeys(
            repoId = 1,
            prevKey = null,
            nextKey = 3
        )

        val state = createPagingState<Int, RepositoryEntity>(
            listOf(
                lastItem
            )
        )

        coEvery { localDataSource.getRemoteKeysRepoId(1) } returns remoteKeys

        val response = mockk<SearchRepositoriesResponseDto>()
        every { response.items } returns emptyList()

        coEvery {
            api.searchKotlinRepositories(
                page = 3,
                perPage = any()
            )
        } returns response

        coEvery { localDataSource.saveRepositories(any(), any(), any(), any()) } just Runs

        // When
        mediator.load(
            LoadType.APPEND,
            state
        )

        // Then
        coVerify {
            api.searchKotlinRepositories(
                page = 3,
                perPage = state.config.pageSize
            )
        }
    }

    @Test
    fun `should return error when api throws exception`() = runTest {
        // Given
        val state = createPagingState<Int, RepositoryEntity>()

        coEvery {
            api.searchKotlinRepositories(page = any())
        } throws RuntimeException()

        // When
        val result = mediator.load(
            LoadType.REFRESH,
            state
        )

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}
