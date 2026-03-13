package com.glins.android.apps.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glins.android.apps.presentation.screen.RepositoryDetailsScreen
import com.glins.android.apps.presentation.screen.RepositoryListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Box(
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navController,
            startDestination = "repositories"
        ) {
            val animationDuration = 500
            composable(
                route = "repositories",
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(
                            durationMillis = animationDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(
                            durationMillis = animationDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                RepositoryListScreen(
                    onRepositoryClick = { repo ->
                        navController.navigate(
                            "repositoryDetails/${repo.id}"
                        )
                    }
                )
            }

            composable(
                route = "repositoryDetails/{repoId}",
                arguments = listOf(
                    navArgument("repoId") {
                        type = NavType.LongType
                    }
                ),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(
                            durationMillis = animationDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(
                            durationMillis = animationDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                RepositoryDetailsScreen(
                    onBackClick = {
                        val previousScreen = navController.previousBackStackEntry
                        if (previousScreen != null) {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}