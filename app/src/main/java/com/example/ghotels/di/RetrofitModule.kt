package com.example.ghotels.di

import com.example.ghotels.data.source.remote.AttendanceServiceClient
import com.example.ghotels.data.source.remote.DepartmentServiceClient
import com.example.ghotels.data.source.remote.EmployeeServiceClient
import com.example.ghotels.data.source.remote.OfficialHolidayServiceClient
import com.example.ghotels.data.source.remote.PasswordResetTokenServiceClient
import com.example.ghotels.data.source.remote.PermissionRequestServiceClient
import com.example.ghotels.data.source.remote.RoleServiceClient
import com.example.ghotels.data.source.remote.PermissionTypeServiceClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8080/")
            //.baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<EmployeeServiceClient> {
        get<Retrofit>().create(EmployeeServiceClient::class.java)
    }

    single<OfficialHolidayServiceClient> {
        get<Retrofit>().create(OfficialHolidayServiceClient::class.java)
    }

    single<PermissionTypeServiceClient> {
        get<Retrofit>().create(PermissionTypeServiceClient::class.java) // ← NUEVO
    }

    single<AttendanceServiceClient> {
        get<Retrofit>().create(AttendanceServiceClient::class.java)
    }

    single<EmployeeServiceClient> {
        get<Retrofit>().create(EmployeeServiceClient::class.java)
    }

    single<RoleServiceClient> {
        get<Retrofit>().create(RoleServiceClient::class.java)
    }

    single<DepartmentServiceClient> {
        get<Retrofit>().create(DepartmentServiceClient::class.java)
    }

    single<PermissionRequestServiceClient> {
        get<Retrofit>().create(PermissionRequestServiceClient::class.java) // ← NUEVO
    }

    single<PasswordResetTokenServiceClient> {
        get<Retrofit>().create(PasswordResetTokenServiceClient::class.java)
    }
}
