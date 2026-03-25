package com.glins.android.common.routes

import kotlinx.serialization.Serializable

@Serializable object RepositoryListRoute

@Serializable data class RepositoryDetailsRoute(val repoId: Long)