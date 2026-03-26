package com.glins.android.ui.utils

import com.glins.android.domain.error.DomainError
import com.glins.android.ui.R

fun DomainError.toErrorMessageRes(): Int = when (this) {
    DomainError.Network -> R.string.network_exception
    DomainError.GithubRequestLimit -> R.string.github_request_limit_exception
    DomainError.GithubSearchLimit -> R.string.github_search_limit_exception
    DomainError.Server -> R.string.server_unavailable_exception
    DomainError.NotFound -> R.string.details_repository_not_found
    DomainError.Unknown -> R.string.repositories_default_error
    DomainError.Unexpected -> R.string.details_unexpected_error
}