package com.glins.android.apps.domain.error

sealed class DomainError {
    data object Network : DomainError()
    data object GithubRequestLimit : DomainError()
    data object GithubSearchLimit : DomainError()
    data object Server : DomainError()
    data object Unknown : DomainError()
}