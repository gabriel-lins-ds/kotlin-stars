package com.glins.android.apps.data.repository

import com.glins.android.apps.data.local.KotlinStarsLocalDataSource
import com.glins.android.apps.data.remote.KotlinStarsRemoteDataSource
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.repository.KotlinStarsRepository

class KotlinStarsRepositoryImpl (
    private val remoteDataSource: KotlinStarsRemoteDataSource,
    private val localDataSource: KotlinStarsLocalDataSource
) : KotlinStarsRepository {
    override suspend fun getTopRepositories(page: Int): List<Repository> {
        return try {
            val remoteRepos = remoteDataSource.getRepositories(page)

            localDataSource.saveRepositories(
                remoteRepos.map { it.toEntity() }
            )

            remoteRepos.map { it.toDomain() }
        } catch (_: Exception) {
            localDataSource.getRepositories()
                .map { it.toDomain() }
        }
    }
}