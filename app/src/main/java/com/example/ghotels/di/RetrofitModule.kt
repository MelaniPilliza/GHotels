package com.example.ghotels.di

import com.example.ghotels.data.source.remote.AsistenciaServiceClient
import com.example.ghotels.data.source.remote.DepartamentoServiceClient
import com.example.ghotels.data.source.remote.EmpleadoServiceClient
import com.example.ghotels.data.source.remote.FestivoServiceClient
import com.example.ghotels.data.source.remote.PerfilServiceClient
import com.example.ghotels.data.source.remote.RolServiceClient
import com.example.ghotels.data.source.remote.SolicitudPermisoServiceClient
import com.example.ghotels.data.source.remote.TipoPermisoServiceClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<EmpleadoServiceClient> {
        get<Retrofit>().create(EmpleadoServiceClient::class.java)
    }

    single<FestivoServiceClient> {
        get<Retrofit>().create(FestivoServiceClient::class.java)
    }

    single<TipoPermisoServiceClient> {
        get<Retrofit>().create(TipoPermisoServiceClient::class.java) // ← NUEVO
    }

    single<AsistenciaServiceClient> {
        get<Retrofit>().create(AsistenciaServiceClient::class.java)
    }

    single<EmpleadoServiceClient> {
        get<Retrofit>().create(EmpleadoServiceClient::class.java)
    }
}
