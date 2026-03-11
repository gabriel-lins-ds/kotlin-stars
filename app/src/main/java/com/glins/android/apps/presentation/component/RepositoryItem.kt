package com.glins.android.apps.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.glins.android.apps.core.constants.NetworkConstants.AVATAR_SIZE
import com.glins.android.apps.core.constants.NetworkConstants.GITHUB_URL_AVATAR_SIZE_SUFFIX
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor

@Composable
fun RepositoryItem(
    repository: Repository,
    modifier: Modifier = Modifier,
    index: Int = 0,
    onClick: (Repository) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(repository) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "$index",
                Modifier.padding(8.dp)
            )

            AsyncImage(
                model = repository.author.iconUrl + GITHUB_URL_AVATAR_SIZE_SUFFIX,
                contentDescription = "Owner avatar",
                modifier = Modifier
                    .size(AVATAR_SIZE.dp)
                    .clip(CircleShape)
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
                    text = repository.author.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
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

                Row {
                    Text(
                        text = "⭐ ${repository.stars}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "🍴 ${repository.forks}",
                        style = MaterialTheme.typography.bodySmall
                    )
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
        name = "kotlin",
        description = "The Kotlin Programming Language.",
        url = "https://github.com/JetBrains/kotlin",
        stars = 52439,
        forks = 6247,
        author = RepositoryAuthor(
            name = "JetBrains",
            iconUrl = "",
            profileUrl = "https://github.com/JetBrains"
        )
    )
    RepositoryItem(repository = repository)
}