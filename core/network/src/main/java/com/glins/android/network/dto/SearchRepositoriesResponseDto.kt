package com.glins.android.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoriesResponseDto(
    @SerialName("total_count")
    val totalCount: Int,
    val items: List<RepositoryDto>
)
