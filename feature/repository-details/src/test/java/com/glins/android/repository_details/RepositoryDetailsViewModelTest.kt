package com.glins.android.repository_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.glins.android.common.routes.RepositoryDetailsRoute
import com.glins.android.domain.error.DomainError
import com.glins.android.domain.usecase.GetRepositoryByIdUseCase
import com.glins.android.testing.createRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryDetailsViewModelTest {

    private val getRepositoryByIdUseCase: GetRepositoryByIdUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val defaultRepoId = 1L

    @BeforeEach
    fun initialize() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `should emit Success when repository exists`() = runTest {
        val repositoryModel = createRepository(id = defaultRepoId)
        coEvery { getRepositoryByIdUseCase(defaultRepoId) } returns Result.success(repositoryModel)

        val viewModel = createViewModel(repoId = defaultRepoId)

        viewModel.state.test {
            assertEquals(RepositoryDetailsUiState.Loading, awaitItem())
            assertEquals(RepositoryDetailsUiState.Success(repositoryModel), awaitItem())
        }
    }

    @Test
    fun `should emit Error when repository does not exist`() = runTest {
        coEvery { getRepositoryByIdUseCase(defaultRepoId) } returns Result.success(null)

        val viewModel = createViewModel(repoId = defaultRepoId)

        viewModel.state.test {
            assertEquals(RepositoryDetailsUiState.Loading, awaitItem())
            assertEquals(RepositoryDetailsUiState.Error(DomainError.NotFound), awaitItem())
        }
    }

    @Test
    fun `should emit Error state with unexpected error when repository fails`() = runTest {
        coEvery { getRepositoryByIdUseCase(any()) } returns Result.failure(RuntimeException("Network Error"))

        val viewModel = createViewModel(repoId = 123L)

        viewModel.state.test {
            assertEquals(RepositoryDetailsUiState.Loading, awaitItem())
            assertEquals(RepositoryDetailsUiState.Error(DomainError.Unexpected), awaitItem())
        }
    }

    private fun createViewModel(repoId: Long = 0): RepositoryDetailsViewModel {
        val handle = SavedStateHandle()

        every {
            handle.toRoute(RepositoryDetailsRoute::class, any())
        } returns RepositoryDetailsRoute(repoId)

        return RepositoryDetailsViewModel(
            savedStateHandle = handle,
            getRepositoryByIdUseCase = getRepositoryByIdUseCase
        )
    }
}
