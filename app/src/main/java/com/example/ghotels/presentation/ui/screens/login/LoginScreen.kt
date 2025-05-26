package com.example.ghotels.presentation.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ghotels.presentation.navigation.Screen
import com.example.ghotels.presentation.viewmodel.login.LoginViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = koinViewModel() // Inyectado con Koin
) {
    val mail by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()


    LaunchedEffect(loginSuccess) {
        loginSuccess?.let {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true } // Evita volver atrás con el botón
            }
        }
    }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar sesión",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Introduce tu dirección de correo electrónico y tu contraseña",
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = mail,
                onValueChange = { loginViewModel.updateMail(it) },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { loginViewModel.updatePassword(it) },
                label = { Text("Contraseña") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(38.dp))

            Button(
                onClick = { loginViewModel.doLogin() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF003366),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Text(text = "Iniciar sesión")
            }

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            loginSuccess?.let {
                Text(
                    text = "¡Bienvenido/a, ${it.nombre}!",
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(top = 12.dp)
                )

                when (it.rol) {
                    "ADMIN", "RRHH" -> {
                        // Pantalla o botones para administradores/RRHH
                    }
                    else -> {
                        // Pantalla para empleados u otros roles personalizados
                    }
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = { /* TODO: recuperar contraseña */ }) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color(0xFF003366)
                )
            }
        }
    }
}