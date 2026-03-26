package com.glins.android.data.mapper

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.glins.android.common.constants.CommonConstants.NETWORK_PAGE_SIZE
import com.glins.android.testing.createRepositoryDto
import com.glins.android.testing.createRepositoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryMapperTest {

    @Test
    fun `should map dto list to entities with correct index`() {
        // Given
        val dtoList = listOf(
            createRepositoryDto(id = 1),
            createRepositoryDto(id = 2)
        )

        val page = 2
        val lastUpdated = 123L

        // When
        val result = dtoList.toEntity(page, lastUpdated)

        // Then
        assertEquals(2, result.size)
        assertEquals(((page - 1) * NETWORK_PAGE_SIZE) + 1, result[0].index)
        assertEquals(((page - 1) * NETWORK_PAGE_SIZE) + 2, result[1].index)
        assertEquals(lastUpdated, result[0].lastUpdated)
    }

    @Test
    fun `should map repository entity to domain model`() {
        // Given
        val entity = createRepositoryEntity()

        // When
        val result = entity.toDomain()

        // Then
        assertEquals(entity.id, result.id)
        assertEquals(entity.name, result.name)
        assertEquals(entity.stars, result.stars)
        assertEquals(entity.ownerName, result.author.name)
        assertEquals(entity.ownerAvatar, result.author.iconUrl)
        assertEquals(entity.ownerUrl, result.author.profileUrl)
    }

    @Test
    fun `should map paging data entities to domain`() = runTest {
        // Given
        val entity = createRepositoryEntity()

        val flow = flowOf(
            PagingData.from(listOf(entity))
        )

        // When
        val result = flow.toDomain()

        // Then
        val items = result.asSnapshot()

        assertEquals(1, items.size)
        assertEquals(entity.id, items.first().id)
    }
}
