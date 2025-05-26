package com.example.ghotels.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Logo : Screen("logo")
    object Home : Screen("home/{rol}") {
        fun createRoute(rol: String) = "home/$rol"
    }
    object Assistance : Screen("assistance")
    object Permission : Screen("permisos")
    object Staff : Screen("staff")
    object Profile : Screen("profile")
    object StaffAdmin : Screen("staffadmin")
    object AddEmployee : Screen("addemployee")
    object PermissionAdmin : Screen("permissionadmin")
    object FestivoAdmin : Screen("festivoadmin")
}
