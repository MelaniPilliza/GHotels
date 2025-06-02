package com.example.ghotels.presentation.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.ghotels.presentation.viewmodel.RecoverPasswordViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecoverPasswordScreen(
    viewModel: RecoverPasswordViewModel = koinViewModel(),
    navController: NavController
) {
    // Estado para el envío del código
    val isLoadingRecovery by viewModel.loadingRecovery.collectAsState()
    val successRecovery by viewModel.successRecovery.collectAsState()
    val errorRecovery by viewModel.errorRecovery.collectAsState()

    // Estado para el restablecimiento con token
    val isLoadingReset by viewModel.loadingReset.collectAsState()
    val successReset by viewModel.successReset.collectAsState()
    val errorReset by viewModel.errorReset.collectAsState()

    // Campos de formulario
    var email by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }

    // Si ya se envió el correo correctamente, mostramos la parte de “restablecer contraseña”
    if (successRecovery == true && successReset != true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Introduce el código recibido y tu nueva contraseña",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF003366),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = token,
                onValueChange = { token = it },
                label = { Text("Código (token)") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Nueva contraseña") },
                singleLine = true,
                visualTransformation = if (newPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (newPasswordVisible)
                        Icons.Default.VisibilityOff
                    else Icons.Default.Visibility

                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (newPasswordVisible) "Ocultar contraseña"
                            else "Mostrar contraseña"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (errorReset != null) {
                Text(errorReset!!, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            }

            Button(
                onClick = {
                    viewModel.reset(token.trim(), newPassword.trim())
                },
                enabled = token.isNotBlank() && newPassword.isNotBlank() && !isLoadingReset,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF003366),
                    contentColor = Color.White
                )
            ) {
                if (isLoadingReset) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(text = "Restablecer contraseña")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Volver al inicio de sesión", color = Color(0xFF005288))
            }
        }

        // Si el restablecimiento fue exitoso, volvemos al login
        LaunchedEffect(successReset) {
            if (successReset == true) {
                navController.popBackStack()
            }
        }

    } else {
        // —— PRIMER PASO: Enviar código al email ——
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recuperar contraseña",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Introduce tu correo y te enviaremos un código.",
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (errorRecovery != null) {
                Text(errorRecovery!!, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            }

            Button(
                onClick = {
                    viewModel.recover(email.trim())
                },
                enabled = email.isNotBlank() && !isLoadingRecovery,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF003366),
                    contentColor = Color.White
                )
            ) {
                if (isLoadingRecovery) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(text = "Enviar código")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Volver al inicio de sesión", color = Color(0xFF005288))
            }
        }

        // Cuando se complete el envío, el Composable se recompone y mostrará la segunda parte
        LaunchedEffect(successRecovery) {
            // No hace falta acción adicional: la recomposición mostrará el bloque superior
        }
    }
}


