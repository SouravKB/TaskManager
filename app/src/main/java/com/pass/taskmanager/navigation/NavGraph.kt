package com.pass.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pass.taskmanager.pages.ProjectPage
import com.pass.taskmanager.viewmodels.AuthViewModel
import com.pass.taskmanager.views.AuthPage

const val authRoute = "AuthPage"
const val projectsRoute = "ProjectsPage"

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = authRoute,
    ) {
        composable(route = authRoute) {
            AuthPage(
                viewModel,
                navigateToNextPage = {
                    navController.navigate(projectsRoute)
                }
            )
        }
        composable(route = projectsRoute) {
            ProjectPage(
                navigateToAuthPage = {
                    navController.popBackStack()
                    navController.navigate(authRoute)
                }
            )
        }
    }
}
