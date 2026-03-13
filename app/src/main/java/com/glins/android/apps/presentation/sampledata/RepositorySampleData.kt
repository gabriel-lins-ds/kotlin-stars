package com.glins.android.apps.presentation.sampledata

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor

object RepositorySampleData {

    fun repository(
        id: Long = 1,
        index: Int = 1,
        name: String = "kotlin-stars",
        author: String = "JetBrains"
    ) = Repository(
        id = id,
        index = index,
        name = name,
        url = "https://github.com/JetBrains/kotlin",
        description = LoremIpsum(50).values.first(),
        stars = (1..9999999).random(),
        forks = (1..9999999).random(),
        author = RepositoryAuthor(
            name = author,
            iconUrl = "",
            profileUrl = ""
        )
    )

    fun repositoryList(count: Int = 20): List<Repository> {
        return List(count) {
            repository(id = it.toLong(), index = it + 1)
        }
    }
}