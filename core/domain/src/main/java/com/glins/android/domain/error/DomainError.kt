package com.glins.android.domain.error

sealed class DomainError(val isRetryable: Boolean = true) {
    data object Network : DomainError()
    data object GithubRequestLimit : DomainError()
    data object GithubSearchLimit : DomainError(isRetryable = false)
    data object Server : DomainError()
    data object NotFound : DomainError()
    data object Unknown : DomainError()
    data object Unexpected : DomainError()
}
