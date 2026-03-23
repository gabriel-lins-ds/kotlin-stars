package com.glins.android.apps.ui.repositorylist

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.glins.android.apps.domain.error.DomainException
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.R
import com.glins.android.apps.ui.constants.UiConstants.FAB_VISIBILITY_THRESHOLD
import com.glins.android.apps.ui.constants.UiConstants.REFRESH_TIME_THRESHOLD
import com.glins.android.apps.ui.component.ErrorView
import com.glins.android.apps.ui.component.LoadingView
import com.glins.android.apps.ui.component.RepositoryItem
import com.glins.android.apps.ui.sampledata.RepositorySampleData
import com.glins.android.apps.ui.testtag.TestTags
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListContent(
    repositories: LazyPagingItems<Repository>,
    onRepositoryClick: (Repository) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    fabVisibilityThreshold: Int = FAB_VISIBILITY_THRESHOLD
) {
    val listState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex >= fabVisibilityThreshold }
    }
    val scope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing = repositories.loadState.refresh is LoadState.Loading
    var lastRefreshTime by remember { mutableLongStateOf(0L) }

    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            CenterAlignedTopAppBar(
                {
                    Text(stringResource(R.string.repo_list_top_bar_title))
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFab,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight }
                ) + fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            listState.scrollToItem(0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                        .testTag(TestTags.LIST_SCROLL_TO_TOP_FAB),
                    shape = CircleShape
                ) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_up),
                        contentDescription = stringResource(
                            R.string.scroll_to_top_content_description
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                val now = System.currentTimeMillis()

                if (now - lastRefreshTime > REFRESH_TIME_THRESHOLD) {
                    lastRefreshTime = now
                    scope.launch {
                        repositories.refresh()
                        delay(300)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
                .testTag(TestTags.LIST_PULL_TO_REFRESH_BOX)
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .testTag(TestTags.LIST_LAZY_LAYOUT)
            ) {
                items(
                    count = repositories.itemCount
                ) { index ->
                    repositories[index]?.let { repo ->
                        RepositoryItem(
                            repository = repo,
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
                        val exception = (repositories.loadState.append as? LoadState.Error)?.error
                        val error = (exception as? DomainException)?.error

                        item {
                            ErrorView(
                                error = error,
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
                    if (repositories.itemCount == 0) {
                        LoadingView()
                    }
                }
                is LoadState.Error -> {
                    val exception = (repositories.loadState.refresh as? LoadState.Error)?.error
                    val error = (exception as? DomainException)?.error

                    if (repositories.itemCount == 0) {
                        ErrorView(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .pointerInput(Unit) {},
                            error = error,
                            onRetry = onRetry
                        )
                    } else {
                        val context = LocalContext.current
                        error?.stringRes?.let {
                            Toast.makeText(
                                context,
                                stringResource(it),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryListContentPreview() {

    val fakeFlow = remember {
        flowOf(PagingData.from(RepositorySampleData.repositoryList(10)))
    }

    val lazyPagingItems = fakeFlow.collectAsLazyPagingItems()

    MaterialTheme {
        RepositoryListContent(
            repositories = lazyPagingItems,
            onRepositoryClick = {},
            onRetry = {}
        )
    }
}