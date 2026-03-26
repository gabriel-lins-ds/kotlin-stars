package com.glins.android.repository_list

import androidx.paging.PagingData
import app.cash.turbine.test
import com.glins.android.domain.model.Repository
import com.glins.android.domain.usecase.GetRepositoriesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryListViewModelTest {

    private val getRepositoriesUseCase: GetRepositoriesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch repositories from use case on initialization`() = runTest {
        // Given
        val pagingData = PagingData.empty<Repository>()
        every { getRepositoriesUseCase() } returns flowOf(pagingData)

        // When
        val viewModel = RepositoryListViewModel(getRepositoriesUseCase)

        // Then
        viewModel.repositories.test {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}