package com.glins.android.apps.ui.repositorydetails

import androidx.lifecycle.SavedStateHandle
import com.glins.android.apps.domain.repository.KotlinStarsRepository
import com.glins.android.apps.tests.fixtures.createRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailsViewModelTest {

    private val repository: KotlinStarsRepository = mockk()

    @Test
    fun `should emit Success when repository exists`() = runTest {
        // Given
        val repoId = 1L
        val repositoryModel = createRepository(id = repoId)

        coEvery { repository.getRepositoryById(repoId) } returns repositoryModel

        val savedStateHandle = SavedStateHandle(
            mapOf("repoId" to repoId)
        )

        // When
        val viewModel = RepositoryDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository
        )

        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertTrue(state is RepositoryDetailsUiState.Success)
    }

    @Test
    fun `should emit Error when repository does not exist`() = runTest {
        // Given
        val repoId = 1L

        coEvery { repository.getRepositoryById(repoId) } returns null

        val savedStateHandle = SavedStateHandle(
            mapOf("repoId" to repoId)
        )

        // When
        val viewModel = RepositoryDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository
        )

        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(
            RepositoryDetailsUiState.Error("Repository not found"),
            state
        )
    }

    @Test
    fun `should emit Error when repository throws exception`() = runTest {
        // Given
        val repoId = 1L

        coEvery { repository.getRepositoryById(repoId) } throws RuntimeException("boom")

        val savedStateHandle = SavedStateHandle(
            mapOf("repoId" to repoId)
        )

        // When
        val viewModel = RepositoryDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository
        )

        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(
            RepositoryDetailsUiState.Error("boom"),
            state
        )
    }
}
