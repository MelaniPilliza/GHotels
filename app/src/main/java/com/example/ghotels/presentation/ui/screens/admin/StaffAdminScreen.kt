package com.example.ghotels.presentation.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.data.model.UserDto
import com.example.ghotels.presentation.navigation.Screen
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.viewmodel.StaffViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffAdminScreen(
    navController: NavController,
    viewModel: StaffViewModel = koinViewModel()
) {
    val employees by viewModel.employees.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF002B50)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopGHotels(title = "Empleados")
                    Spacer(modifier = Modifier.height(8.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "+ Añadir empleado",
                            color = Color(0xFFB3E5FC),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.AddEmployee.route)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(employees) { employee ->
                    EmployeeAdminCard(employee = employee, onEditClick = {
                        // TODO: Navegar a pantalla de edición
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }

            MenuGHotels(
                selectedIndex = 5,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            FloatingAdminMenu(navController = navController)
        }
    }
}


@Composable
fun EmployeeAdminCard(employee: UserDto, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
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
                    text = listOf(employee.firstName, employee.lastName)
                        .flatMap { it.split(" ") }
                        .take(2)
                        .joinToString("") { it.firstOrNull()?.uppercase() ?: "" },
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${employee.firstName} ${employee.lastName}", fontWeight = FontWeight.Medium)
                Text(text = employee.role.orEmpty(), fontSize = 12.sp, color = Color.Gray)

            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF007AFF))
            }
        }
    }
}
