package com.glins.android.apps.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object RepositoryListRoute

@Serializable data class RepositoryDetailsRoute(val repoId: Long)