package com.example.ghotels.di

import com.example.ghotels.data.local.UserDataStore
import com.example.ghotels.data.repository.AttendanceRepository
import com.example.ghotels.data.repository.DepartmentRepository
import com.example.ghotels.data.repository.EmployeeRepository
import com.example.ghotels.data.repository.OfficialHolidayRepository
import com.example.ghotels.data.repository.PasswordResetTokenRepository
import com.example.ghotels.data.repository.PermissionRequestRepository
import com.example.ghotels.data.repository.RoleRepository
import com.example.ghotels.data.repository.PermissionTypeRepository
import com.example.ghotels.domain.usecase.attendance.GetOpenAttendanceUseCase
import com.example.ghotels.domain.usecase.attendance.ListAttendanceUseCase
import com.example.ghotels.domain.usecase.attendance.RegisterAttendanceUseCase
import com.example.ghotels.domain.usecase.department.AddDepartmentUseCase
import com.example.ghotels.domain.usecase.permissiontype.AddPermissionTypeUseCase
import com.example.ghotels.domain.usecase.role.AddRoleUseCase
import com.example.ghotels.domain.usecase.department.ListDepartmentUseCase
import com.example.ghotels.domain.usecase.permissiontype.ListPermissionTypeUseCase
import com.example.ghotels.domain.usecase.role.ListRoleUseCase
import com.example.ghotels.domain.usecase.employee.LoginUseCase
import com.example.ghotels.domain.usecase.employee.RegisterEmployeeUseCase
import com.example.ghotels.domain.usecase.employee.SaveUserUseCase
import com.example.ghotels.domain.usecase.employee.UpdateProfileUseCase
import com.example.ghotels.domain.usecase.department.DeleteDepartmentUseCase
import com.example.ghotels.domain.usecase.department.UpdateDepartmentUseCase
import com.example.ghotels.domain.usecase.employee.DeleteEmployeeUseCase
import com.example.ghotels.domain.usecase.employee.GetEmployeeByIdUseCase
import com.example.ghotels.domain.usecase.employee.ListEmployeeUseCase
import com.example.ghotels.domain.usecase.employee.UpdateEmployeeUseCase
import com.example.ghotels.domain.usecase.officialholiday.AddOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.DeleteOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.ListOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.officialholiday.UpdateOfficialHolidayUseCase
import com.example.ghotels.domain.usecase.password.RecoverPasswordUseCase
import com.example.ghotels.domain.usecase.password.ResetPasswordUseCase
import com.example.ghotels.domain.usecase.permissionrequest.AddPermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ApprovePermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.GetPermissionBalancesUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ListEmployeePermissionRequestsUseCase
import com.example.ghotels.domain.usecase.permissionrequest.ListPermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissionrequest.RejectPermissionRequestUseCase
import com.example.ghotels.domain.usecase.permissiontype.DeletePermissionTypeUseCase
import com.example.ghotels.domain.usecase.permissiontype.UpdatePermissionTypeUseCase
import com.example.ghotels.domain.usecase.role.DeleteRoleUseCase
import com.example.ghotels.domain.usecase.role.UpdateRoleUseCase
import com.example.ghotels.presentation.viewmodel.RegisterEmployeeViewModel
import com.example.ghotels.presentation.viewmodel.AttendanceViewModel
import com.example.ghotels.presentation.viewmodel.DepartmentViewModel
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.presentation.viewmodel.PermissionRequestViewModel
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import com.example.ghotels.presentation.viewmodel.ProfileViewModel
import com.example.ghotels.presentation.viewmodel.RecoverPasswordViewModel
import com.example.ghotels.presentation.viewmodel.RoleViewModel
import com.example.ghotels.presentation.viewmodel.StaffViewModel
import com.example.ghotels.presentation.viewmodel.login.LoginViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // REPOSITORIOS ----------------------------------------
    single { EmployeeRepository(get()) }
    single { OfficialHolidayRepository(get()) }
    single { DepartmentRepository(get()) }
    single { RoleRepository(get()) }
    single { PermissionTypeRepository(get()) }
    single { AttendanceRepository(get()) }
    single { PermissionRequestRepository(get()) }
    single { PasswordResetTokenRepository(get()) }

    // CASOS DE USO ----------------------------------------

    //-- Login y usuario
    factory { LoginUseCase(get()) }
    single { UserDataStore(get()) }
    single { SaveUserUseCase(get()) }

    //-- Empleados
    single { ListEmployeeUseCase(get()) }
    single { RegisterEmployeeUseCase(get()) }
    single { UpdateEmployeeUseCase(get()) }
    single { UpdateProfileUseCase(get()) }
    single { DeleteEmployeeUseCase(get()) }
    single { GetEmployeeByIdUseCase(get()) }



    //-- Festivos oficiales
    single { ListOfficialHolidayUseCase(get()) }
    single { AddOfficialHolidayUseCase(get()) }
    single { UpdateOfficialHolidayUseCase(get()) }
    single { DeleteOfficialHolidayUseCase(get()) }

    //-- Departamentos
    single { ListDepartmentUseCase(get()) }
    single { AddDepartmentUseCase(get()) }
    single { UpdateDepartmentUseCase(get()) }
    single { DeleteDepartmentUseCase(get()) }

    //-- Roles
    single { ListRoleUseCase(get()) }
    single { AddRoleUseCase(get()) }
    single { UpdateRoleUseCase(get()) }
    single { DeleteRoleUseCase(get()) }

    //-- Tipos de permiso
    single { ListPermissionTypeUseCase(get()) }
    single { AddPermissionTypeUseCase(get()) }
    single { UpdatePermissionTypeUseCase(get()) }
    single { DeletePermissionTypeUseCase(get()) }

    //--Solicitudes de permiso
    single { AddPermissionRequestUseCase(get()) }
    single { ListPermissionRequestUseCase(get()) }
    single { ListEmployeePermissionRequestsUseCase(get()) }
    single { ApprovePermissionRequestUseCase(get()) }
    single { RejectPermissionRequestUseCase(get()) }
    single { GetPermissionBalancesUseCase(get()) }


    //-- Asistencia
    single { ListAttendanceUseCase(get()) }
    single { RegisterAttendanceUseCase(get()) }
    single { GetOpenAttendanceUseCase(get()) }


    //-- Recuperación de contraseña
    single { RecoverPasswordUseCase(get()) }
    single { ResetPasswordUseCase(get()) }


    // VIEWMODELS ------------------------------------------
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { OfficialHolidayViewModel(get(), get(), get(), get()) }
    viewModel { PermissionTypeViewModel(get(), get(), get(), get()) }
    viewModel { AttendanceViewModel(get(), get(), get()) }
    viewModel { StaffViewModel(get() ,get() ,get() ,get())}
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RegisterEmployeeViewModel(get()) }
    viewModel { RoleViewModel(get(), get(), get(), get()) }
    viewModel { DepartmentViewModel(get(), get(), get(), get()) }
    viewModel { PermissionRequestViewModel(get(),get(),get(),get(),get(), get()) }
    viewModel { RecoverPasswordViewModel(get(), get()) }
}
