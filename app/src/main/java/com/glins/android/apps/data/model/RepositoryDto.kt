package com.glins.android.apps.data.model

import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor
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
) {
    fun toDomain(): Repository {
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

    fun toEntity(): RepositoryEntity {
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
}