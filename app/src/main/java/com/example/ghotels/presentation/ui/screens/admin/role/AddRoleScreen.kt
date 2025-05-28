package com.example.ghotels.presentation.ui.screens.admin.role

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ghotels.presentation.viewmodel.RoleViewModel

@Composable
fun AddRoleScreen(
    navController: NavController,
    viewModel: RoleViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isValid = name.isNotBlank()

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002B50)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopGHotels(title = "Nuevo rol")
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
                            label = { Text("Nombre del rol") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descripción (opcional)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                viewModel.addRole(name, description)
                                navController.popBackStack()
                            },
                            enabled = isValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Guardar rol")
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
