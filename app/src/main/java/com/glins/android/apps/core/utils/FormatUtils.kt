package com.glins.android.apps.core.utils

object FormatUtils {
    fun Int.formatBigNumber(): String {
        return when {
            this >= 1_000_000 -> {
                val value = this / 1_000_000.0
                if (value % 1 < 0.049) "${value.toInt()}M" else "%.1fM".format(value)
            }
            this >= 1_000 -> {
                val value = this / 1_000.0
                if (value % 1 < 0.049) "${value.toInt()}k" else "%.1fk".format(value)
            }
            else -> this.toString()
        }
    }
}