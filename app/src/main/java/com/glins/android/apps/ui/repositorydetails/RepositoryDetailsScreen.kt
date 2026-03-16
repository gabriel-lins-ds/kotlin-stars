package com.glins.android.apps.ui.repositorydetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.glins.android.apps.R
import com.glins.android.apps.data.constants.NetworkConstants.GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX
import com.glins.android.apps.ui.constants.UiConstants.AVATAR_SIZE_DETAILS
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.ui.component.ErrorView
import com.glins.android.apps.ui.component.GithubAuthorImage
import com.glins.android.apps.ui.component.InfoChip
import com.glins.android.apps.ui.component.LoadingView
import com.glins.android.apps.ui.sampledata.RepositorySampleData
import com.glins.android.apps.ui.testtag.TestTags
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryDetailsScreen(
    viewModel: RepositoryDetailsViewModel = koinViewModel(),
    onOpenUrlClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val state: RepositoryDetailsUiState by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        RepositoryDetailsUiState.Loading -> LoadingView()

        is RepositoryDetailsUiState.Error -> ErrorView(
            modifier = Modifier.padding(16.dp),
            defaultErrorMessage = (state as RepositoryDetailsUiState.Error).message,
            onRetry = { }
        )

        is RepositoryDetailsUiState.Success -> RepositoryDetailsContent(
            repository = (state as RepositoryDetailsUiState.Success).repository,
            onOpenUrlClick = onOpenUrlClick,
            onBackClick = onBackClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailsContent(
    repository: Repository,
    onOpenUrlClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        repository.name,
                        modifier = Modifier.testTag(TestTags.DETAILS_TOP_APP_BAR_REPO_NAME)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "#${repository.index}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GithubAuthorImage(
                url = repository.author.iconUrl + GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX,
                size = AVATAR_SIZE_DETAILS
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = repository.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag(TestTags.DETAILS_BODY_REPO_NAME)
            )

            Text(
                text = repository.author.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.testTag(TestTags.DETAILS_BODY_AUTHOR_NAME)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoChip(R.drawable.ic_star, repository.stars.toString())
                InfoChip(R.drawable.ic_fork, repository.forks.toString())
            }

            Spacer(modifier = Modifier.height(24.dp))

            repository.description?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.testTag(TestTags.DETAILS_BODY_REPO_DESCRIPTION)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onOpenUrlClick(repository.url)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.details_open_github_button))
            }

            Button(
                onClick = {
                    onOpenUrlClick(repository.author.profileUrl)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.details_open_github_profile_button))
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RepositoryDetailsScreenPreview() {
    RepositoryDetailsContent(
        repository = RepositorySampleData.repository(),
        onOpenUrlClick = {},
        onBackClick = {}
    )
}