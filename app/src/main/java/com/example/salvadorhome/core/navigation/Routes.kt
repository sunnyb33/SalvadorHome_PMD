package com.example.salvadorhome.core.navigation

sealed class Routes(val route: String) {

    object Welcome : Routes("welcome")

    object Login : Routes("login")

    object Register : Routes("register")

    object RoleSelection : Routes("role_selection")

    object Home : Routes("home")
}