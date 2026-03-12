package com.glins.android.apps.data.model

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    val url: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val owner: RepositoryOwnerDto,
    @SerializedName("forks_count")
    val forkCount: Int
)