package com.glins.android.apps.data.dto

data class SearchRepositoriesResponseDto(
    val totalCount: Int,
    val items: List<RepositoryDto>
)
