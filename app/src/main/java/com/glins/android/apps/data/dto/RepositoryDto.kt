package com.glins.android.apps.data.dto

import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor
import kotlinx.serialization.SerialName
import java.security.acl.Owner

data class RepositoryDto(
    val id: Long,
    val name: String,
    val url: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val owner: RepositoryOwnerDto,
    @SerialName("forks_count")
    val forkCount: Int
) {
    fun toDomain(): Repository {
        return Repository(
            id = id,
            name = name,
            stars = stargazersCount,
            forks = forkCount,
            author = RepositoryAuthor(
                name = owner.login,
                iconUrl = owner.avatarUrl,
                profileUrl = owner.htmlUrl
            )
        )
    }

    fun toEntity(): RepositoryEntity {
        return RepositoryEntity(
            id = id,
            name = name,
            stars = stargazersCount,
            forks = forkCount,
            ownerName = owner.login,
            ownerAvatar = owner.avatarUrl,
            ownerUrl = owner.htmlUrl
        )
    }
}