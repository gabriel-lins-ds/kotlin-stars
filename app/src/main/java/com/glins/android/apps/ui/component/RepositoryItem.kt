package com.glins.android.apps.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glins.android.apps.R
import com.glins.android.apps.data.constants.NetworkConstants.GITHUB_URL_AVATAR_SIZE_SUFFIX
import com.glins.android.apps.ui.constants.UiConstants.AVATAR_SIZE
import com.glins.android.apps.ui.util.FormatUtils.formatBigNumber
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor

@Composable
fun RepositoryItem(
    repository: Repository,
    modifier: Modifier = Modifier,
    onClick: (Repository) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                role = Role.Button,
                onClick = { onClick(repository) }
            )   ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Box(modifier = Modifier.padding(16.dp)) {

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp).align(Alignment.TopEnd),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${repository.index}",
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                GithubAuthorImage(
                    modifier = Modifier.padding(start = 8.dp),
                    url = repository.author.iconUrl + GITHUB_URL_AVATAR_SIZE_SUFFIX,
                    size = AVATAR_SIZE
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = repository.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = stringResource(R.string.repository_item_author_prefix, repository.author.name),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (!repository.description.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = repository.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.ic_star),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )

                        Text(
                            text = repository.stars.formatBigNumber(),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(Modifier.width(12.dp))

                        Icon(
                            painterResource(R.drawable.ic_fork), // representa fork
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )

                        Text(
                            text = repository.forks.formatBigNumber(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun RepositoryItemPreview() {
    val repository = Repository(
        id = 3432266,
        index = 1000,
        name = "kotlin",
        description = "The Kotlin Programming Language.",
        url = "https://github.com/JetBrains/kotlin",
        stars = 52439,
        forks = 6050,
        author = RepositoryAuthor(
            name = "JetBrains",
            iconUrl = "",
            profileUrl = "https://github.com/JetBrains"
        )
    )
    RepositoryItem(repository = repository)
}