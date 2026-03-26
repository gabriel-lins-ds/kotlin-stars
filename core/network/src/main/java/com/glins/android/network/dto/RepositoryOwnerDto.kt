package com.glins.android.network.dto

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val login: String
)