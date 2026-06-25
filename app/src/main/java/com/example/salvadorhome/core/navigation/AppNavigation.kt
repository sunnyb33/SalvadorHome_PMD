package com.example.salvadorhome.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.salvadorhome.features.auth.ui.LoginScreen
import com.example.salvadorhome.features.auth.ui.RegisterScreen
import com.example.salvadorhome.features.auth.ui.RoleSelectionScreen
import com.example.salvadorhome.features.auth.ui.WelcomeScreen
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModelProvider
import com.example.salvadorhome.features.home.ui.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelProvider.provideFactory(context)
    )

    NavHost(
        navController = navController,
        startDestination = Routes.Welcome.route
    ) {

        composable(Routes.Welcome.route) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Routes.Login.route)
                },
                onRegistrerClick = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSucces = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Routes.RoleSelection.route)
                }
            )
        }

        composable(Routes.RoleSelection.route) {
            RoleSelectionScreen(
                viewModel = authViewModel,
                onRoleConfirmed = {
                    authViewModel.register {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(
                onPropertyClick = {},
                onNavItemClick = {}
            )
        }
    }
}