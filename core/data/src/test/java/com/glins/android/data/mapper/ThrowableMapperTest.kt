package com.glins.android.data.mapper

import com.glins.android.domain.error.DomainError
import com.glins.android.testing.createHttpException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.IOException

class ThrowableMapperTest {

    @Test
    fun `should return Network when throwable is IOException`() {
        // Given
        val throwable = IOException()

        // When
        val result = throwable.toDomainError()

        // Then
        assertEquals(DomainError.Network, result)
    }

    @Test
    fun `should return GithubRequestLimit when http code is 403`() {
        // Given
        val exception = createHttpException(403)

        // When
        val result = exception.toDomainError()

        // Then
        assertEquals(DomainError.GithubRequestLimit, result)
    }

    @Test
    fun `should return GithubRequestLimit when http code is 429`() {
        // Given
        val exception = createHttpException(429)

        // When
        val result = exception.toDomainError()

        // Then
        assertEquals(DomainError.GithubRequestLimit, result)
    }

    @Test
    fun `should return GithubSearchLimit when http code is 422`() {
        // Given
        val exception = createHttpException(422)

        // When
        val result = exception.toDomainError()

        // Then
        assertEquals(DomainError.GithubSearchLimit, result)
    }

    @Test
    fun `should return Server when http code is 500`() {
        // Given
        val exception = createHttpException(500)

        // When
        val result = exception.toDomainError()

        // Then
        assertEquals(DomainError.Server, result)
    }

    @Test
    fun `should return Unknown when http code is unexpected`() {
        // Given
        val exception = createHttpException(418)

        // When
        val result = exception.toDomainError()

        // Then
        assertEquals(DomainError.Unknown, result)
    }

    @Test
    fun `should return Unknown when throwable is not mapped`() {
        // Given
        val throwable = IllegalStateException()

        // When
        val result = throwable.toDomainError()

        // Then
        assertEquals(DomainError.Unknown, result)
    }
}
