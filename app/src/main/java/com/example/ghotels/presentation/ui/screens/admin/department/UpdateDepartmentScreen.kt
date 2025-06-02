package com.example.ghotels.presentation.ui.screens.admin.department

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ghotels.presentation.navigation.Screen
import com.example.ghotels.presentation.viewmodel.DepartmentViewModel

@Composable
fun UpdateDepartmentScreen(
    navController: NavController,
    departmentId: Long?,
    viewModel: DepartmentViewModel = koinViewModel()
) {
    val departments by viewModel.departments.collectAsState()
    val currentDepartment = remember(departments) { departments.find { it.id == departmentId } }

    var name by remember { mutableStateOf("") }
    val isValid = name.isNotBlank()

    LaunchedEffect(currentDepartment) {
        currentDepartment?.let {
            name = it.name
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopGHotels(title = "Editar Departamento")
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nombre del departamento") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            OutlinedButton(
                                onClick = { navController.popBackStack() },
                                border = BorderStroke(1.dp, Color.Gray),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Volver")
                            }

                            Button(
                                onClick = {
                                    viewModel.updateDepartment(departmentId, name)
                                    navController.navigate(Screen.DepartmentAdmin.route) {
                                        popUpTo(Screen.DepartmentAdmin.route) { inclusive = true }
                                    }
                                },
                                enabled = isValid,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Guardar cambios")
                            }
                        }
                    }
                }
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
