package com.glins.android.domain.error

sealed class DomainError {
    data object Network : DomainError()
    data object GithubRequestLimit : DomainError()
    data object GithubSearchLimit : DomainError()
    data object Server : DomainError()
    data object NotFound : DomainError()
    data object Unknown : DomainError()
    data object Unexpected : DomainError()
}