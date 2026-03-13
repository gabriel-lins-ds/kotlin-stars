package com.glins.android.apps.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.glins.android.apps.R
import com.glins.android.apps.core.constants.NetworkConstants.GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX
import com.glins.android.apps.core.constants.NetworkConstants.GITHUB_URL_AVATAR_SIZE_SUFFIX
import com.glins.android.apps.core.constants.UiConstants.AVATAR_SIZE
import com.glins.android.apps.core.constants.UiConstants.AVATAR_SIZE_DETAILS
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor
import com.glins.android.apps.presentation.component.ErrorView
import com.glins.android.apps.presentation.component.GithubAuthorImage
import com.glins.android.apps.presentation.component.InfoChip
import com.glins.android.apps.presentation.component.LoadingView
import com.glins.android.apps.presentation.state.RepositoryDetailsUiState
import com.glins.android.apps.presentation.viewmodel.RepositoryDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryDetailsScreen(
    viewModel: RepositoryDetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state: RepositoryDetailsUiState by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        RepositoryDetailsUiState.Loading -> LoadingView()

        is RepositoryDetailsUiState.Error -> ErrorView(
            modifier = Modifier.padding(16.dp),
            message = (state as RepositoryDetailsUiState.Error).message,
            onRetry = { }
        )

        is RepositoryDetailsUiState.Success -> RepositoryDetailsContent(
            repository = (state as RepositoryDetailsUiState.Success).repository,
            onBackClick = onBackClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailsContent(
    repository: Repository,
    onBackClick: () -> Unit
) {
    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(repository.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back"
                        )
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
                fontWeight = FontWeight.Bold
            )

            Text(
                text = repository.author.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
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
                            fontWeight = FontWeight.SemiBold
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
//                    onOpenRepositoryClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open on GitHub")
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
    val repo = Repository(
        id = 1,
        name = "kotlin",
        url = "https://www.kotlin.com",
        description = "Kotlin é uma linguagem de programação de alto nível",
        stars = 52439,
        forks = 6247,
        author = RepositoryAuthor(
            name = "JetBrains",
            iconUrl = "",
            profileUrl = "https://github.com/JetBrains"
        )
    )

    RepositoryDetailsContent(
        repository = repo,
        onBackClick = {}
    )
}