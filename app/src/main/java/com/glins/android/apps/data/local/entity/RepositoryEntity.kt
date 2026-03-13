package com.glins.android.apps.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "repositories",
    indices = [Index("stars")]
)
data class RepositoryEntity(
    @PrimaryKey
    val id: Long,
    val index: Int,
    val name: String,
    val description: String? = null,
    val url: String,
    val stars: Int,
    val forks: Int,
    val ownerName: String,
    val ownerAvatar: String,
    val ownerUrl: String,
    val lastUpdated: Long
)