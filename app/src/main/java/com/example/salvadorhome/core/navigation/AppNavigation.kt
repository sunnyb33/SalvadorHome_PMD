package com.example.salvadorhome.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salvadorhome.features.host.ui.HostApp
import com.example.salvadorhome.features.auth.ui.LoginScreen
import com.example.salvadorhome.features.auth.ui.RegisterScreen
import com.example.salvadorhome.features.auth.ui.RoleSelectionScreen
import com.example.salvadorhome.features.auth.ui.WelcomeScreen
import com.example.salvadorhome.features.auth.viewmodel.AuthState
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModelFactory
import com.example.salvadorhome.features.guest.ui.GuestApp

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var currentRole by remember { mutableStateOf("") }
    var pendingEmail by remember { mutableStateOf("") }
    var pendingPassword by remember { mutableStateOf("") }
    var pendingNombre by remember { mutableStateOf("") }
    var pendingApellido by remember { mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            val rol = (authState as AuthState.Success).rol
            currentRole = rol

            if (rol.equals("Arrendador", ignoreCase = true)) {
                navController.navigate(Routes.Host.route) {
                    popUpTo(Routes.Welcome.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Welcome.route) {
                        inclusive = true
                    }
                }
            }

            authViewModel.resetState()
        }
    }

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
                onLoginSuccess = { rol ->
                    currentRole = rol

                    if (rol.equals("Arrendador", ignoreCase = true)) {
                        navController.navigate(Routes.Host.route) {
                            popUpTo(Routes.Welcome.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onContinueToRole = { email, password, nombre, apellido ->
                    pendingEmail = email
                    pendingPassword = password
                    pendingNombre = nombre
                    pendingApellido = apellido

                    navController.navigate(Routes.RoleSelection.route)
                }
            )
        }

        composable(Routes.RoleSelection.route) {
            RoleSelectionScreen(
                onRoleSelected = { rolSeleccionado ->
                    authViewModel.register(
                        email = pendingEmail,
                        clave = pendingPassword,
                        nombre = pendingNombre,
                        apellido = pendingApellido,
                        rol = rolSeleccionado
                    )
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

    fun navigateByRole(rol: String) {
        currentRole = rol

        if (rol.equals("Arrendador", ignoreCase = true)) {
            navController.navigate(Routes.Host.route) {
                popUpTo(Routes.Welcome.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Welcome.route) {
                    inclusive = true
                }
            }
        }
    }
}
