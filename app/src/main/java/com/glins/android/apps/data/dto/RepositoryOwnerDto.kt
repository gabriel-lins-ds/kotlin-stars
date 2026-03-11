package com.glins.android.apps.data.dto

import kotlinx.serialization.SerialName

data class RepositoryOwnerDto(
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val login: String
)