package com.example.ghotels

import android.app.Application
import com.example.ghotels.di.appModule
import com.example.ghotels.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin() {
            androidContext(this@MyApp)
            modules(listOf(retrofitModule, appModule))
        }
    }
}