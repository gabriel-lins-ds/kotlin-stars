package com.glins.android.apps.data.remote

import com.glins.android.apps.data.dto.RepositoryDto
import com.glins.android.apps.data.remote.api.KotlinStarsApi

class KotlinStarsRemoteDataSource(
    private val api: KotlinStarsApi
) {

    suspend fun getRepositories(page: Int): List<RepositoryDto> {
        return api.searchKotlinRepositories(page = page).items
    }
}