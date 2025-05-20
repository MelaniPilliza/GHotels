package com.example.ghotels

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ghotels.di.appModule
import com.example.ghotels.di.retrofitModule
import com.example.ghotels.presentation.ui.screens.login.LoginScreen
import com.example.ghotels.ui.theme.AppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                LoginScreen()
            }
        }
    }
}
