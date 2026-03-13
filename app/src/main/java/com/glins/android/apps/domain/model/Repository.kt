package com.glins.android.apps.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Repository(
    val id: Long,
    val index: Int,
    val name: String,
    val description: String? = null,
    val url: String,
    val stars: Int,
    val forks: Int,
    val author: RepositoryAuthor
)
