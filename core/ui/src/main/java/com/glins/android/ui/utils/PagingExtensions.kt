package com.glins.android.ui.utils

import androidx.paging.LoadState
import com.glins.android.domain.error.DomainError
import com.glins.android.domain.error.DomainException

fun LoadState.Error.toDomainError(): DomainError? {
    return (error as? DomainException)?.error
}
