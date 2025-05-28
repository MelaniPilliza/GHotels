package com.example.ghotels.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Logo : Screen("logo")

    object Home : Screen("home/{role}") {
        fun createRoute(role: String) = "home/$role"
    }

    object OfficialHoliday : Screen("officialholiday")
    object Attendance : Screen("attendance")
    object Permission : Screen("permission")
    object Staff : Screen("staff")
    object Profile : Screen("profile")

    // Admin Screens
    object StaffAdmin : Screen("staffadmin")
    object AddEmployee : Screen("addemployee")

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

    object RoleAdmin : Screen("roleadmin")
    object AddRole : Screen("addrole")
    object EditRole : Screen("editrole/{id}") {
        fun createRoute(id: Long) = "editrole/$id"
    }

    object DepartmentAdmin : Screen("departmentadmin")
    object AddDepartment : Screen("adddepartment")
    object EditDepartment : Screen("editdepartment/{id}") {
        fun createRoute(id: Long) = "editdepartment/$id"
    }

    object PermissionRequest : Screen("permissionrequest")
    object PermissionRequestAdmin : Screen("permissionrequestadmin")
}
