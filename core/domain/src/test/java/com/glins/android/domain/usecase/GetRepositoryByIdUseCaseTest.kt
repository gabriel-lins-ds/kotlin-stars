package com.glins.android.domain.usecase

import com.glins.android.domain.model.Repository
import com.glins.android.domain.model.RepositoryAuthor
import com.glins.android.domain.repository.KotlinStarsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetRepositoryByIdUseCaseTest {

    private val repository: KotlinStarsRepository = mockk()
    private val useCase = GetRepositoryByIdUseCase(repository)

    private val sampleRepo = Repository(
        id = 1L,
        index = 1,
        name = "Kotlin",
        description = "Language",
        url = "url",
        stars = 100,
        forks = 50,
        author = RepositoryAuthor("JetBrains", "avatar", "profileUrl")
    )

    @Test
    fun `invoke should return success with repository when repository returns it`() = runTest {
        // Given
        coEvery { repository.getRepositoryById(1L) } returns sampleRepo

        // When
        val result = useCase(1L)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(sampleRepo, result.getOrNull())
    }

    @Test
    fun `invoke should return success with null when repository returns null`() = runTest {
        // Given
        coEvery { repository.getRepositoryById(1L) } returns null

        // When
        val result = useCase(1L)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull())
    }

    @Test
    fun `invoke should return failure when repository throws exception`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { repository.getRepositoryById(1L) } throws exception

        // When
        val result = useCase(1L)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
