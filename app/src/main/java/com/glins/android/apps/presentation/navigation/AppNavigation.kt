package com.glins.android.apps.presentation.navigation

import androidx.compose.runtime.Composable
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

    NavHost(
        navController = navController,
        startDestination = "repositories"
    ) {
        composable("repositories") {
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
            )
        ) {
            RepositoryDetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}