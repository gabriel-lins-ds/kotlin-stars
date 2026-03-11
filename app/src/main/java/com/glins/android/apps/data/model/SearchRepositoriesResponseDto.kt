package com.glins.android.apps.data.model

data class SearchRepositoriesResponseDto(
    val totalCount: Int,
    val items: List<RepositoryDto>
)
