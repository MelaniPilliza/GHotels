package com.example.ghotels.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ghotels.data.repository.AsistenciaRepository
import com.example.ghotels.data.repository.DepartamentoRepository
import com.example.ghotels.data.repository.EmpleadoRepository
import com.example.ghotels.data.repository.PerfilRepository
import com.example.ghotels.data.repository.RolRepository
import com.example.ghotels.data.repository.SolicitudPermisoRepository
import com.example.ghotels.data.repository.TipoPermisoRepository
import com.example.ghotels.domain.usecase.LoginUseCase
import com.example.ghotels.presentation.viewmodel.login.LoginViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {

    // Repositories
    single { EmpleadoRepository(get()) }

    // UseCases
    factory { LoginUseCase(get()) }

    // ViewModels
    viewModel  { LoginViewModel(get()) }
}
