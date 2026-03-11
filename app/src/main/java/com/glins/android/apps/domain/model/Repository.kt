package com.glins.android.apps.domain.model

data class Repository(
    val id: Long,
    val name: String,
    val stars: Int,
    val forks: Int,
    val author: RepositoryAuthor
)
