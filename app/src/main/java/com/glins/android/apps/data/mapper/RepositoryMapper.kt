package com.glins.android.apps.data.mapper

import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.data.model.RepositoryDto
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor

fun RepositoryEntity.toDomain(): Repository {
    return Repository(
        id = id,
        name = name,
        description = description,
        url = url,
        stars = stars,
        forks = forks,
        author = RepositoryAuthor(
            name = ownerName,
            iconUrl = ownerAvatar,
            profileUrl = ownerUrl
        )
    )
}

fun RepositoryDto.toDomain(): Repository {
    return Repository(
        id = id,
        name = name,
        description = description,
        url = url,
        stars = stargazersCount,
        forks = forkCount,
        author = RepositoryAuthor(
            name = owner.login,
            iconUrl = owner.avatarUrl,
            profileUrl = owner.htmlUrl
        )
    )
}

fun RepositoryDto.toEntity(): RepositoryEntity {
    return RepositoryEntity(
        id = id,
        name = name,
        description = description,
        url = url,
        stars = stargazersCount,
        forks = forkCount,
        ownerName = owner.login,
        ownerAvatar = owner.avatarUrl,
        ownerUrl = owner.htmlUrl
    )
}