package com.example.ghotels.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ghotels.data.repository.AsistenciaRepository
import com.example.ghotels.data.repository.DepartamentoRepository
import com.example.ghotels.data.repository.EmpleadoRepository
import com.example.ghotels.data.repository.FestivoRepository
import com.example.ghotels.data.repository.PerfilRepository
import com.example.ghotels.data.repository.RolRepository
import com.example.ghotels.data.repository.SolicitudPermisoRepository
import com.example.ghotels.data.repository.TipoPermisoRepository
import com.example.ghotels.domain.usecase.AsistenciaUseCase
import com.example.ghotels.domain.usecase.EmpleadosUseCase
import com.example.ghotels.domain.usecase.FestivoUseCase
import com.example.ghotels.domain.usecase.LoginUseCase
import com.example.ghotels.domain.usecase.RegistrarEmpleadoUseCase
import com.example.ghotels.domain.usecase.SaveUserUseCase
import com.example.ghotels.domain.usecase.TipoPermisoUseCase
import com.example.ghotels.domain.usecase.UpdateProfileUseCase
import com.example.ghotels.presentation.viewmodel.AddEmployeeViewModel
import com.example.ghotels.presentation.viewmodel.AsistenciaViewModel
import com.example.ghotels.presentation.viewmodel.FestivoViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.presentation.viewmodel.PermissionViewModel
import com.example.ghotels.presentation.viewmodel.ProfileViewModel
import com.example.ghotels.presentation.viewmodel.StaffViewModel
import com.example.ghotels.presentation.viewmodel.login.LoginViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // REPOSITORIOS
    single { EmpleadoRepository(get()) }
    single { FestivoRepository(get()) }
    single { TipoPermisoRepository(get()) }
    single { AsistenciaRepository(get()) }
    single { EmpleadoRepository(get()) }

    // USE CASES
    factory { LoginUseCase(get()) }
    single { SaveUserUseCase() }
    single { FestivoUseCase(get()) }
    factory { TipoPermisoUseCase(get()) }
    single { AsistenciaUseCase(get()) }
    single { EmpleadosUseCase(get()) }
    single { UpdateProfileUseCase(get()) }
    single { RegistrarEmpleadoUseCase(get()) }

    // VIEWMODELS
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { FestivoViewModel(get()) }
    viewModel { PermissionViewModel(get()) }
    viewModel { AsistenciaViewModel(get()) }
    viewModel { StaffViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { AddEmployeeViewModel(get()) }
}
