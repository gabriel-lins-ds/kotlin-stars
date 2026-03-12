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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.presentation.component.EmptyRepositoryListView
import com.glins.android.apps.presentation.component.ErrorView
import com.glins.android.apps.presentation.component.LoadingView
import com.glins.android.apps.presentation.component.RepositoryItem
import com.glins.android.apps.presentation.state.RepositoryUiState
import com.glins.android.apps.presentation.viewmodel.RepositoryListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryListScreen(
    viewModel: RepositoryListViewModel = koinViewModel(),
    onRepositoryClick: (Repository) -> Unit
) {
    val repositories =
        viewModel.repositories.collectAsLazyPagingItems()

    RepositoryListContent(
        repositories = repositories,
        onRepositoryClick = onRepositoryClick,
        onRetry = { repositories.retry() }
    )
}

@Composable
fun RepositoryListContent(
    repositories: LazyPagingItems<Repository>,
    onRepositoryClick: (Repository) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(
                count = repositories.itemCount
            ) { index ->
                repositories[index]?.let { repo ->
                    RepositoryItem(
                        repository = repo,
                        index = index,
                        onClick = { onRepositoryClick(it) }
                    )
                }
            }

            when (repositories.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        LoadingView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorView(
                            message = "Erro ao carregar repositórios",
                            onRetry = onRetry,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                else -> Unit
            }
        }

        when (repositories.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingView()
            }

            is LoadState.Error -> {
                ErrorView(
                    message = "Erro ao carregar repositórios",
                    onRetry = onRetry
                )
            }

            else -> Unit
        }
    }
}