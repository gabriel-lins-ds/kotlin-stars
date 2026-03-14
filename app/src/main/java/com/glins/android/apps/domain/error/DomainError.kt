package com.glins.android.apps.domain.error

import androidx.annotation.StringRes
import com.glins.android.apps.R

sealed class DomainError(@StringRes val stringRes: Int) {
    data object Network : DomainError(R.string.network_exception)
    data object GithubRequestLimit : DomainError(R.string.github_request_limit_exception)
    data object GithubSearchLimit : DomainError(R.string.github_search_limit_exception)
    data object Server : DomainError(R.string.server_unavailable_exception)
    data object Unknown : DomainError(R.string.repositories_default_error)
}