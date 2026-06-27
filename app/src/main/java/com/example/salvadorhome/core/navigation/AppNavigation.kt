package com.example.salvadorhome.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salvadorhome.features.host.ui.HostApp
import com.example.salvadorhome.features.auth.ui.LoginScreen
import com.example.salvadorhome.features.auth.ui.RegisterScreen
import com.example.salvadorhome.features.auth.ui.RoleSelectionScreen
import com.example.salvadorhome.features.auth.ui.WelcomeScreen
import com.example.salvadorhome.features.guest.ui.GuestApp

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var currentRole by remember { mutableStateOf("") }
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
                // Corregimos el nombre: onLoginSuccess
                onLoginSuccess = {
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
                // Cuando el registro en Firebase sea exitoso, lo mandamos directo al Home
                onRegisterSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.RoleSelection.route) {
            RoleSelectionScreen(
                onRoleSelected = { rolSeleccionado ->
                    // Por ahora solo navegamos al Home.
                    // Más adelante podemos hacer que esto actualice el rol en Firebase si lo deseas.
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Routes.Home.route) {
            GuestApp(
                userRole = currentRole,
                onHostClick = {
                    navController.navigate(Routes.Host.route)
                }
            )
        }

        composable(Routes.Host.route) {
            HostApp()
        }

    }
}
