package com.glins.android.common.utils

import com.glins.android.common.utils.FormatUtils.formatBigNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormatUtilsTest {

    @Test
    fun `should return same number when value is less than 1000`() {
        // Given
        val value = 532

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("532", result)
    }

    @Test
    fun `should format thousands without decimal when exact`() {
        // Given
        val value = 1000

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("1k", result)
    }

    @Test
    fun `should format thousands with decimal`() {
        // Given
        val value = 1500

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("1.5k", result)
    }

    @Test
    fun `should format millions without decimal when exact`() {
        // Given
        val value = 1_000_000

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("1M", result)
    }

    @Test
    fun `should format millions with decimal`() {
        // Given
        val value = 1_500_000

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("1.5M", result)
    }

    @Test
    fun `should round to integer when decimal part is very small`() {
        // Given
        val value = 2_049_999

        // When
        val result = value.formatBigNumber()

        // Then
        assertEquals("2M", result)
    }
}