package com.glins.android.data.repository

import com.glins.android.database.datasource.KotlinStarsLocalDataSource
import com.glins.android.data.paging.RepositoryPagerFactory
import com.glins.android.network.api.KotlinStarsApi
import com.glins.android.testing.createRepositoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KotlinStarsRepositoryImplTest {

    private val localDataSource: KotlinStarsLocalDataSource = mockk()
    private val api: KotlinStarsApi = mockk()
    private val pagerFactory: RepositoryPagerFactory = mockk()

    private lateinit var repository: KotlinStarsRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = KotlinStarsRepositoryImpl(localDataSource, pagerFactory)
    }

    @Test
    fun `should return domain repository when entity exists`() = runTest {
        // Given
        val entity = createRepositoryEntity(1)

        coEvery {
            localDataSource.getRepositoryById(1)
        } returns entity

        // When
        val result = repository.getRepositoryById(1)

        // Then
        assertEquals(entity.id, result?.id)
    }

    @Test
    fun `should return null when repository does not exist`() = runTest {
        // Given
        coEvery {
            localDataSource.getRepositoryById(1)
        } returns null

        // When
        val result = repository.getRepositoryById(1)

        // Then
        assertNull(result)
    }
}
