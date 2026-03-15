package com.glins.android.apps.util

object FormatUtils {
    fun Int.formatBigNumber(): String = when {
        this >= 1_000_000 -> formatNumber(this / 1_000_000.0, "M")
        this >= 1_000     -> formatNumber(this / 1_000.0, "k")
        else              -> this.toString()
    }

    private fun formatNumber(value: Double, suffix: String): String =
        if (value % 1 < 0.05) "${value.toInt()}$suffix" else "%.1f$suffix".format(value)
}