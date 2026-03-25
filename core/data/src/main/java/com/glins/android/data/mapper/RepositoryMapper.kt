package com.glins.android.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.glins.android.common.constants.CommonConstants.NETWORK_PAGE_SIZE
import com.glins.android.network.dto.RepositoryDto
import com.glins.android.database.entity.RepositoryEntity
import com.glins.android.domain.model.Repository
import com.glins.android.domain.model.RepositoryAuthor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun List<RepositoryDto>.toEntity(
    page: Int,
    lastUpdated: Long = System.currentTimeMillis()
): List<RepositoryEntity> {
    return mapIndexed { index, repo ->
        repo.toEntity(
            index = ((page - 1) * NETWORK_PAGE_SIZE) + (index + 1),
            lastUpdated = lastUpdated
        )
    }
}

private fun RepositoryDto.toEntity(index: Int, lastUpdated: Long): RepositoryEntity {
    return RepositoryEntity(
        id = id,
        index = index,
        name = name,
        description = description,
        url = htmlUrl,
        stars = stargazersCount,
        forks = forkCount,
        ownerName = owner.login,
        ownerAvatar = owner.avatarUrl,
        ownerUrl = owner.htmlUrl,
        lastUpdated = lastUpdated
    )
}


fun Flow<PagingData<RepositoryEntity>>.toDomain(): Flow<PagingData<Repository>> {
    return map { it.toDomain() }
}

private fun PagingData<RepositoryEntity>.toDomain(): PagingData<Repository> {
    return map { it.toDomain() }
}

fun RepositoryEntity.toDomain(): Repository {
    return Repository(
        id = id,
        index = index,
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
