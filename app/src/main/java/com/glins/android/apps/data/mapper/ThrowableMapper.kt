package com.glins.android.apps.data.mapper

import com.glins.android.apps.domain.error.DomainError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toDomainError(): DomainError {
    return when (this) {
        is IOException -> DomainError.Network

        is HttpException -> when (code()) {
            403, 429 -> DomainError.GithubRequestLimit
            422 -> DomainError.GithubSearchLimit
            in 500..599 -> DomainError.Server
            else -> DomainError.Unknown
        }

        else -> DomainError.Unknown
    }
}