package com.glins.android.database

import com.glins.android.database.entity.RepositoryEntity

fun createRepositoryEntity(
    id: Long = 1
) = RepositoryEntity(
    id = id,
    index = 1,
    name = "repo",
    description = "desc",
    url = "url",
    stars = 10,
    forks = 5,
    ownerName = "author",
    ownerAvatar = "avatar",
    ownerUrl = "profile",
    lastUpdated = 123L
)