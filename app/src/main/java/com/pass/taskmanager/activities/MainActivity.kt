package com.pass.taskmanager.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.pass.taskmanager.navigation.AppNavGraph
import com.pass.taskmanager.navigation.projectListRoute
import com.pass.taskmanager.viewmodels.AuthViewModel
import com.pass.taskmanager.viewmodels.ProjectViewModel

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModel.AuthVMFactory(this)
    }
    private val projectVm by viewModels<ProjectViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberAnimatedNavController()
            AppNavGraph(navController, viewModel, projectVm)
            checkAuthStatus()
            getAuthState()
        }
    }

    private fun checkAuthStatus() {
        if (viewModel.isUserAuthenticated) {
            navController.navigate(projectListRoute)
        }
    }

    private fun getAuthState() = viewModel.getAuthState()
}
