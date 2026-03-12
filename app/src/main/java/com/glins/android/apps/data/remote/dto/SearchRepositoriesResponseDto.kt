package com.glins.android.apps.data.remote.dto

data class SearchRepositoriesResponseDto(
    val totalCount: Int,
    val items: List<RepositoryDto>
)
