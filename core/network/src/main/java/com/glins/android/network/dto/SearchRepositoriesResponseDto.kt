package com.glins.android.network.dto

data class SearchRepositoriesResponseDto(
    val totalCount: Int,
    val items: List<RepositoryDto>
)
