package com.example.ghotels.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.presentation.viewmodel.StaffViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffScreen(navController: NavController, viewModel: StaffViewModel = koinViewModel()) {
    val listaEmpleados by viewModel.empleados.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopGHotels(titulo = "Personal")

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
            ) {
                LaunchedEffect(listaEmpleados) {
                    Log.d("STAFF_SCREEN", "📦 Empleados recibidos en UI: ${listaEmpleados.size}")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp)
                ) {
                    items(items = listaEmpleados) { empleado ->
                        EmpleadoCard(empleado)
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }

        MenuGHotels(
            selectedIndex = 3,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun EmpleadoCard(empleado: UserDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF00556E)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = listOf(empleado.nombre, empleado.apellidos)
                    .flatMap { it.split(" ") }
                    .take(2)
                    .joinToString("") { it.firstOrNull()?.uppercase() ?: "" },
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "${empleado.nombre} ${empleado.apellidos}",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Text(
                text = empleado.rol,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun StaffScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        StaffScreen(navController = navController)
    }
}

