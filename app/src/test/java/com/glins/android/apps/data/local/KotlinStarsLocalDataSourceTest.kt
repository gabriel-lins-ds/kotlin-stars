package com.glins.android.apps.data.local

import com.glins.android.apps.data.local.dao.RemoteKeysDao
import com.glins.android.apps.data.local.dao.RepositoryDao
import com.glins.android.apps.data.local.database.AppDatabase
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.tests.fixtures.createRepositoryEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KotlinStarsLocalDataSourceTest {

    private val repositoryDao: RepositoryDao = mockk()
    private val remoteKeysDao: RemoteKeysDao = mockk()
    private val appDatabase: AppDatabase = mockk(relaxed = true)

    private lateinit var dataSource: KotlinStarsLocalDataSource

    @BeforeEach
    fun setup() {
        dataSource = KotlinStarsLocalDataSource(
            repositoryDao,
            remoteKeysDao,
            appDatabase
        )

        coEvery { repositoryDao.insertRepositories(any()) } just Runs
        coEvery { remoteKeysDao.insertAll(any()) } just Runs
        coEvery { repositoryDao.clear() } just Runs
        coEvery { remoteKeysDao.clearRemoteKeys() } just Runs
    }

    @Test
    fun `should clear tables when refreshing repositories`() = runTest {
        // Given
        val repos = listOf(createRepositoryEntity(1))

        // When
        dataSource.saveRepositoriesInternal(
            repos = repos,
            page = 1,
            isRefresh = true,
            hasReachedEndOfPagination = false
        )

        // Then
        coVerify { remoteKeysDao.clearRemoteKeys() }
        coVerify { repositoryDao.clear() }
    }

    @Test
    fun `should insert repositories when save repositories called`() = runTest {
        // Given
        val repos = listOf(createRepositoryEntity(1))

        // When
        dataSource.saveRepositoriesInternal(
            repos = repos,
            page = 1,
            isRefresh = false,
            hasReachedEndOfPagination = false
        )

        // Then
        coVerify { repositoryDao.insertRepositories(repos) }
    }

    @Test
    fun `should generate correct remote keys when saving repositories`() = runTest {
        // Given
        val repos = listOf(createRepositoryEntity(1))
        val keysSlot = slot<List<RemoteKeys>>()

        coEvery { remoteKeysDao.insertAll(capture(keysSlot)) } just Runs

        // When
        dataSource.saveRepositoriesInternal(
            repos = repos,
            page = 2,
            isRefresh = false,
            hasReachedEndOfPagination = false
        )

        // Then
        val key = keysSlot.captured.first()

        assertEquals(1, key.prevKey)
        assertEquals(3, key.nextKey)
        assertEquals(1, key.repoId)
    }
}
