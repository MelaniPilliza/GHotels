package com.example.ghotels.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Logo : Screen("logo")

    object Home : Screen("home/{role}") {
        fun createRoute(role: String) = "home/$role"
    }

    object OfficialHoliday : Screen("officialholiday")
    object Attendance : Screen("attendance")
    object Balance : Screen("balance")
    object Staff : Screen("staff")
    object Profile : Screen("profile")

    // Admin Screens
    object StaffAdmin : Screen("staffadmin")
    object RegisterEmployee : Screen("addemployee")

    object PermissionAdmin : Screen("permissionadmin")
    object AddPermission : Screen("addpermission")
    object EditPermission : Screen("editpermission/{id}") {
        fun createRoute(id: Long) = "editpermission/$id"
    }

    object OfficialHolidayAdmin : Screen("officialholidayadmin")
    object AddOfficialHoliday : Screen("addofficialholiday")
    object EditOfficialHoliday : Screen("editofficialholiday/{id}") {
        fun createRoute(id: Long) = "editofficialholiday/$id"
    }

    object RoleAdmin : Screen("roles")
    object AddRole : Screen("roles/add")
    object UpdateRole : Screen("roles/update/{id}") {
        fun createRoute(id: Long): String = "roles/update/$id"
    }



    object DepartmentAdmin : Screen("departments")
    object AddDepartment : Screen("departments/add")
    object UpdateDepartment : Screen("departments/update/{id}") {
        fun createRoute(id: Long): String = "departments/update/$id"
    }

    object PermissionRequest : Screen("permissionrequest")
    object PermissionRequestAdmin : Screen("permissionrequestadmin")
}
