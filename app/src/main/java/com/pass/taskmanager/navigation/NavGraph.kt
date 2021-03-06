package com.pass.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pass.taskmanager.pages.ProjectListPage
import com.pass.taskmanager.pages.ProjectPage
import com.pass.taskmanager.viewmodels.AuthViewModel
import com.pass.taskmanager.viewmodels.ProjectViewModel
import com.pass.taskmanager.views.AuthPage

const val authRoute = "AuthPage"
const val projectListRoute = "ProjectListPage"
const val projectsRoute = "ProjectsPage"

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel,
    projectVm: ProjectViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = authRoute,
    ) {
        composable(route = authRoute) {
            AuthPage(
                viewModel,
                navigateToNextPage = {
                    navController.navigate(projectListRoute)
                }
            )
        }
        composable(route = projectListRoute) {
            ProjectListPage(projectVm = projectVm)
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
