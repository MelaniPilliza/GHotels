package com.example.ghotels.presentation.ui.screens.admin.permissiontype

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
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun AddPermissionTypeScreen(
    navController: NavController,
    viewModel: PermissionTypeViewModel = koinViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var requiereAprobacion by remember { mutableStateOf(false) }
    var ilimitado by remember { mutableStateOf(false) }
    var diasDisponibles by remember { mutableStateOf("") }

    val isValid = nombre.isNotBlank() && descripcion.isNotBlank() &&
            (ilimitado || diasDisponibles.toIntOrNull() != null)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF002B50)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopGHotels(title = "Nuevo permiso")
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre del permiso") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = requiereAprobacion,
                                onCheckedChange = { requiereAprobacion = it }
                            )
                            Text("Requiere aprobación")
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = ilimitado,
                                onCheckedChange = {
                                    ilimitado = it
                                    if (ilimitado) diasDisponibles = ""
                                }
                            )
                            Text("Permiso ilimitado")
                        }

                        if (!ilimitado) {
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = diasDisponibles,
                                onValueChange = { diasDisponibles = it },
                                label = { Text("Días disponibles por año") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                val dias = if (ilimitado) null else diasDisponibles.toIntOrNull()
                                viewModel.addPermissionType(
                                    name = nombre,
                                    description = descripcion,
                                    requiresApproval = requiereAprobacion,
                                    unlimited = ilimitado,
                                    availableDays = dias
                                )
                                navController.popBackStack()
                            },
                            enabled = isValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Guardar permiso")
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
