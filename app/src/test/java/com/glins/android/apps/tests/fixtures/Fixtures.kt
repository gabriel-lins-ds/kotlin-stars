package com.glins.android.apps.tests.fixtures

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.glins.android.apps.data.local.entity.RemoteKeys
import com.glins.android.apps.data.local.entity.RepositoryEntity
import com.glins.android.apps.data.remote.dto.RepositoryDto
import com.glins.android.apps.data.remote.dto.RepositoryOwnerDto
import com.glins.android.domain.model.Repository
import com.glins.android.domain.model.RepositoryAuthor
import retrofit2.HttpException

fun <Key : Any, Value : Any> createPagingState(
    data: List<Value> = emptyList(),
    pageSize: Int = 30
): PagingState<Key, Value> {
    return PagingState(
        pages = if (data.isEmpty()) {
            emptyList()
        } else {
            listOf(
                PagingSource.LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = null
                )
            )
        },
        anchorPosition = null,
        config = PagingConfig(pageSize = pageSize),
        leadingPlaceholderCount = 0
    )
}

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


fun createRepositoryDto(id: Long) = RepositoryDto(
    id = id,
    name = "repo",
    description = "desc",
    htmlUrl = "url",
    stargazersCount = 10,
    forkCount = 5,
    owner = RepositoryOwnerDto(
        login = "author",
        avatarUrl = "avatar",
        htmlUrl = "profile"
    )
)

fun createRepository(
    id: Long = 1
): Repository {
    return Repository(
        id = id,
        index = 1,
        name = "repo",
        description = "desc",
        url = "url",
        stars = 100,
        forks = 10,
        author = RepositoryAuthor(
            name = "author",
            iconUrl = "avatar",
            profileUrl = "profile"
        )
    )
}


fun createRemoteKeys(
    repoId: Long,
    prevKey: Int? = null,
    nextKey: Int? = null
) = RemoteKeys(
    repoId = repoId,
    prevKey = prevKey,
    nextKey = nextKey
)

fun createHttpException(code: Int): HttpException {
    val response = retrofit2.Response.error<Any>(
        code,
        okhttp3.ResponseBody.create(null, "")
    )
    return HttpException(response)
}