package com.glins.android.apps.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.presentation.component.EmptyRepositoryListView
import com.glins.android.apps.presentation.component.RepositoryItem
import com.glins.android.apps.presentation.state.RepositoryUiState
import com.glins.android.apps.presentation.viewmodel.RepositoryListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryListScreen(
    viewModel: RepositoryListViewModel = koinViewModel(),
    onRepositoryClick: (Repository) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    RepositoryListContent(
        state = state,
        onRepositoryClick = onRepositoryClick,
        onLoadNextPage = viewModel::loadNextPage,
        onRetry = viewModel::retry
    )
}

@Composable
fun RepositoryListContent(
    state: RepositoryUiState,
    onRepositoryClick: (Repository) -> Unit,
    onLoadNextPage: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            RepositoryUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is RepositoryUiState.Error -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }

            is RepositoryUiState.Success -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    if (state.repositories.isEmpty()) {
                        item {
                            EmptyRepositoryListView()
                        }
                    }

                    itemsIndexed(
                        items = state.repositories,
                        key = { _, repo -> repo.id }
                    ) { index, repo ->
                        RepositoryItem(
                            repository = repo,
                            onClick = onRepositoryClick
                        )

                        if (index == state.repositories.lastIndex) {
                            onLoadNextPage()
                        }
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}