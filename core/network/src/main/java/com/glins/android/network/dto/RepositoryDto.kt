package com.glins.android.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val owner: RepositoryOwnerDto,
    @SerialName("forks_count")
    val forkCount: Int
)
