package com.glins.android.apps.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class RepositoryOwnerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val login: String
)