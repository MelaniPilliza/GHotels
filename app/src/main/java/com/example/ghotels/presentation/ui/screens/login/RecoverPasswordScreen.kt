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
import androidx.compose.ui.text.font.FontWeight
import com.example.ghotels.ui.theme.AppTheme

@Composable
fun RecoverPasswordScreen() {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
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
            text = "Introduce tu dirección de correo electrónico y te enviaremos un enlace para restablecer tu contraseña.",
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
                .background(Color.White, shape = RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF003366),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Enviar enlace")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = { }) {
            Text(
                text = "Volver al inicio de sesión",
                color = Color(0xFF005288)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RecoverPasswordScreenPreview() {
    AppTheme {
        RecoverPasswordScreen()
    }
}

