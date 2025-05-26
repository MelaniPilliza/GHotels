package com.example.ghotels.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.ui.screens.AssistanceScreen
import com.example.ghotels.presentation.ui.screens.HomeScreen
import com.example.ghotels.presentation.ui.screens.PermissionScreen
import com.example.ghotels.presentation.ui.screens.ProfileScreen
import com.example.ghotels.presentation.ui.screens.StaffScreen
import com.example.ghotels.presentation.ui.screens.admin.AddEmployeeScreen
import com.example.ghotels.presentation.ui.screens.admin.StaffAdminScreen
import com.example.ghotels.presentation.ui.screens.login.LoginScreen
import com.example.ghotels.presentation.ui.screens.logo.LogoScreen


@Composable
fun NavGraph(startDestination: String = Screen.Login.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Logo.route) {
            LogoScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController
            )
        }

        composable(Screen.Permission.route) {
            PermissionScreen(navController)
        }

        composable(Screen.Assistance.route) {
            AssistanceScreen(navController)
        }

        composable(Screen.Staff.route) {
            StaffScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.StaffAdmin.route) {
            StaffAdminScreen(navController)
        }

        composable(Screen.AddEmployee.route) {
            AddEmployeeScreen(navController)
        }

    }
}
