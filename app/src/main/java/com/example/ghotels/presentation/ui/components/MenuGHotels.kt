package com.example.ghotels.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ghotels.presentation.navigation.Screen

@Composable
fun MenuGHotels(
    selectedIndex: Int = 0,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🏠 INICIO
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = if (selectedIndex == 0) Color(0xFF0576CE) else Color.Black
                )
                Text("Inicio", fontSize = 10.sp, color = if (selectedIndex == 0) Color(0xFF0576CE) else Color.Black)
            }

            // 📅 PERMISOS
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screen.Permission.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Permisos",
                    tint = if (selectedIndex == 1) Color(0xFF0576CE) else Color.Black
                )
                Text("Permisos", fontSize = 10.sp, color = if (selectedIndex == 1) Color(0xFF0576CE) else Color.Black)
            }

            // ⏱️ ASISTENCIAS
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screen.Attendance.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Asistencias",
                    tint = if (selectedIndex == 2) Color(0xFF0576CE) else Color.Black
                )
                Text("Asistencias", fontSize = 10.sp, color = if (selectedIndex == 2) Color(0xFF0576CE) else Color.Black)
            }

            // 👥 EMPLEADOS
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screen.Staff.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = "Empleados",
                    tint = if (selectedIndex == 3) Color(0xFF0576CE) else Color.Black
                )
                Text("Empleados", fontSize = 10.sp, color = if (selectedIndex == 3) Color(0xFF0576CE) else Color.Black)
            }

            // 🙍 PERFIL
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = if (selectedIndex == 4) Color(0xFF0576CE) else Color.Black
                )
                Text("Perfil", fontSize = 10.sp, color = if (selectedIndex == 4) Color(0xFF0576CE) else Color.Black)
            }
        }
    }
}
