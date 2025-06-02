package com.example.ghotels.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object RecoverPassword : Screen("recoverpassword")
    object Logo : Screen("logo")

    object Home : Screen("home/{role}") {
        fun createRoute(role: String) = "home/$role"
    }

    object Attendance : Screen("attendance")
    object Balance : Screen("balance")
    object Staff : Screen("staff")
    object Profile : Screen("profile")
    object AllOfficialHoliday : Screen("allofficialholidays")

    // Admin Screens
    object StaffAdmin : Screen("staffadmin")
    object RegisterEmployee : Screen("staffadmin/register")
    object UpdateEmployee : Screen("staffadmin/update/{id}") {
        fun createRoute(id: Long): String = "staffadmin/update/$id"
    }

    object PermissionTypeAdmin : Screen("permissiontypes")
    object AddPermissionType : Screen("permissiontypes/add")
    object UpdatePermissionType : Screen("permissiontypes/update/{id}") {
        fun createRoute(id: Long) = "permissiontypes/update/$id"
    }

    object OfficialHolidayAdmin : Screen("holidays")
    object AddOfficialHoliday : Screen("holidays/add")
    object UpdateOfficialHoliday : Screen("holidays/update/{id}") {
        fun createRoute(id: Long): String = "holidays/update/$id"
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
