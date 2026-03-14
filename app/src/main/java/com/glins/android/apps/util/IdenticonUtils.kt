package com.glins.android.apps.util

import com.glins.android.apps.R

object IdenticonUtils {
    fun getIdenticonRandomId(): Int {
        val dice = (1..6).random()
        return when(dice) {
            1 -> R.drawable.identicon_1
            2 -> R.drawable.identicon_2
            3 -> R.drawable.identicon_3
            4 -> R.drawable.identicon_4
            5 -> R.drawable.identicon_5
            else -> R.drawable.identicon_6
        }
    }
}