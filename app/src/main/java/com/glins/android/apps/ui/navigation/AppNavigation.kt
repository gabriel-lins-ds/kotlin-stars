package com.glins.android.apps.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glins.android.apps.ui.repositorydetails.RepositoryDetailsScreen
import com.glins.android.apps.ui.repositorylist.RepositoryListScreen

@Composable
fun AppNavigation(
    onOpenUrlClick: (String) -> Unit
) {
    val navController = rememberNavController()
    val tweenSpec = remember {
        NavigationDefaults.tweenSpec
    }

    NavHost(
        navController = navController,
        startDestination = RepositoryListRoute,
        enterTransition = { slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tweenSpec
        ) },
        exitTransition = { slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tweenSpec
        ) },
        popEnterTransition = { slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tweenSpec
        ) },
        popExitTransition = { slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tweenSpec
        ) }
    ) {

        composable<RepositoryListRoute> {
            RepositoryListScreen(
                onRepositoryClick = { repo ->
                    navController.navigate(RepositoryDetailsRoute(repo.id))
                }
            )
        }

        composable<RepositoryDetailsRoute> {
            RepositoryDetailsScreen(
                onOpenUrlClick = onOpenUrlClick,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}