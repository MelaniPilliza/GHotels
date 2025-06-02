package com.example.ghotels.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ghotels.presentation.ui.screens.AttendanceScreen
import com.example.ghotels.presentation.ui.screens.BalanceScreen
import com.example.ghotels.presentation.ui.screens.HomeScreen
import com.example.ghotels.presentation.ui.screens.PermissionRequestScreen
import com.example.ghotels.presentation.ui.screens.ProfileScreen
import com.example.ghotels.presentation.ui.screens.StaffScreen
import com.example.ghotels.presentation.ui.screens.admin.department.AddDepartmentScreen
import com.example.ghotels.presentation.ui.screens.admin.RegisterEmployeeScreen
import com.example.ghotels.presentation.ui.screens.admin.role.AddRoleScreen
import com.example.ghotels.presentation.ui.screens.admin.department.DepartmentAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.role.RoleAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.StaffAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.department.UpdateDepartmentScreen
import com.example.ghotels.presentation.ui.screens.admin.officialholiday.AddOfficialHolidayScreen
import com.example.ghotels.presentation.ui.screens.admin.officialholiday.OfficialHolidayAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.permissionrequest.PermissionRequestAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.permissiontype.AddPermissionTypeScreen
import com.example.ghotels.presentation.ui.screens.admin.permissiontype.PermissionTypeAdminScreen
import com.example.ghotels.presentation.ui.screens.admin.role.UpdateRoleScreen
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

        composable(Screen.Balance.route) {
            BalanceScreen(navController)
        }

        composable(Screen.Attendance.route) {
            AttendanceScreen(navController)
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

        composable(Screen.RegisterEmployee.route) {
            RegisterEmployeeScreen(navController)
        }

        composable(Screen.OfficialHolidayAdmin.route) {
            OfficialHolidayAdminScreen(navController)
        }

        composable(Screen.AddOfficialHoliday.route) {
            AddOfficialHolidayScreen(navController)
        }

        composable(Screen.PermissionAdmin.route) {
            PermissionTypeAdminScreen(navController)
        }

        composable(Screen.AddPermission.route) {
            AddPermissionTypeScreen(navController)
        }

        composable(Screen.RoleAdmin.route) {
            RoleAdminScreen(navController)
        }

        composable(Screen.AddRole.route) {
            AddRoleScreen(navController)
        }

        composable(Screen.DepartmentAdmin.route) {
            DepartmentAdminScreen(navController)
        }

        composable(Screen.AddDepartment.route) {
            AddDepartmentScreen(navController)
        }

        composable(Screen.PermissionRequest.route) {
            PermissionRequestScreen(navController)
        }

        composable(Screen.PermissionRequestAdmin.route) {
            PermissionRequestAdminScreen(navController)
        }

        composable(Screen.UpdateRole.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            UpdateRoleScreen(navController, id)
        }


        composable(Screen.UpdateDepartment.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            UpdateDepartmentScreen(navController, id)
        }


    }
}
