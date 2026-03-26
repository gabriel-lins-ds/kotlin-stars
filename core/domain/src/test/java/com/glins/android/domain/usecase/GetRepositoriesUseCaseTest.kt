package com.glins.android.domain.usecase

import androidx.paging.PagingData
import com.glins.android.domain.model.Repository
import com.glins.android.domain.repository.KotlinStarsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRepositoriesUseCaseTest {

    private val repository: KotlinStarsRepository = mockk()
    private val useCase = GetRepositoriesUseCase(repository)

    @Test
    fun `invoke should call repository getRepositories`() {
        // Given
        val pagingData = PagingData.empty<Repository>()
        val flow = flowOf(pagingData)
        every { repository.getRepositories() } returns flow

        // When
        val result = useCase()

        // Then
        assertEquals(flow, result)
        verify(exactly = 1) { repository.getRepositories() }
    }
}
