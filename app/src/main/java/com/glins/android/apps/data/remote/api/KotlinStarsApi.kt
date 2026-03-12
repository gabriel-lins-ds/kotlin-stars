package com.glins.android.apps.data.remote.api

import com.glins.android.apps.core.constants.NetworkConstants.NETWORK_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query
import com.glins.android.apps.data.remote.dto.SearchRepositoriesResponseDto

interface KotlinStarsApi {

    @GET("search/repositories")
    suspend fun searchKotlinRepositories(
        @Query("q") query: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = NETWORK_PAGE_SIZE
    ): SearchRepositoriesResponseDto
}