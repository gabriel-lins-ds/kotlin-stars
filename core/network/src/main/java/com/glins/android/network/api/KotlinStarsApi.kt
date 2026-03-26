package com.glins.android.network.api

import com.glins.android.common.constants.CommonConstants.NETWORK_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query
import com.glins.android.network.dto.SearchRepositoriesResponseDto

interface KotlinStarsApi {

    @GET("search/repositories")
    suspend fun searchKotlinRepositories(
        @Query("q") query: String = DEFAULT_QUERY,
        @Query("sort") sort: String = SORT_STARS,
        @Query("order") order: String = ORDER_DESC,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = NETWORK_PAGE_SIZE
    ): SearchRepositoriesResponseDto

    companion object {
        const val DEFAULT_QUERY = "language:kotlin"
        const val SORT_STARS = "stars"
        const val ORDER_DESC = "desc"
    }
}