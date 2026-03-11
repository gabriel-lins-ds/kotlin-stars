package com.glins.android.apps.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val stars: Int,
    val forks: Int,
    val ownerName: String,
    val ownerAvatar: String,
    val ownerUrl: String
) {
    fun toDomain(): Repository {
        return Repository(
            id = id,
            name = name,
            stars = stars,
            forks = forks,
            author = RepositoryAuthor(
                name = ownerName,
                iconUrl = ownerAvatar,
                profileUrl = ownerUrl
            )
        )
    }
}