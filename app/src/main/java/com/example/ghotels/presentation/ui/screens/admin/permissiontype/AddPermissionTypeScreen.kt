package com.example.ghotels.presentation.ui.screens.admin.permissiontype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.ghotels.presentation.navigation.Screen


@Composable
fun AddPermissionTypeScreen(
    navController: NavController,
    viewModel: PermissionTypeViewModel = koinViewModel()
) {
    var name by rememberSaveable  { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var requiresApproval by rememberSaveable { mutableStateOf(false) }
    var unlimited by rememberSaveable { mutableStateOf(false) }
    var availableDays by rememberSaveable { mutableStateOf("") }

    val availableDaysError =
        !unlimited && availableDays.isNotBlank() && (availableDays.toIntOrNull() == null || (availableDays.toIntOrNull() ?: 0) <= 0)

    val isValid = name.isNotBlank() && (unlimited || (availableDays.toIntOrNull()?.let { it > 0 } == true))


    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                item {
                    TopGHotels(title = "Nuevo Tipo de Permiso")
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Tarjeta con formulario
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Nombre 
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Nombre *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Descripción (opcional)
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Descripción (opcional)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Requiere aprobación
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = requiresApproval,
                                    onCheckedChange = { requiresApproval = it }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Requiere aprobación")
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            // Ilimitado
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = unlimited,
                                    onCheckedChange = {
                                        unlimited = it
                                        if (it) {
                                            // Si pasa a Ilimitado, limpiamos el campo
                                            availableDays = ""
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Ilimitado")
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            // Días disponibles (solo si no es ilimitado)
                            if (!unlimited) {
                                OutlinedTextField(
                                    value = availableDays,
                                    onValueChange = { availableDays = it },
                                    label = { Text("Días disponibles *") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    isError = availableDaysError,
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                if (availableDaysError) {
                                    Text(
                                        text = "Introduce un número válido mayor que 0.",
                                        color = Color.Red,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            // Botones Volver / Guardar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = { navController.popBackStack() },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Volver")
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = {
                                        val days = if (unlimited) null else availableDays.toIntOrNull()
                                        viewModel.addPermissionType(
                                            name = name.trim(),
                                            description = description.trim(),
                                            requiresApproval = requiresApproval,
                                            unlimited = unlimited,
                                            availableDays = days
                                        )
                                        navController.navigate(Screen.PermissionTypeAdmin.route) {
                                            popUpTo(Screen.PermissionTypeAdmin.route) { inclusive = true }
                                        }
                                    },
                                    enabled = isValid,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Guardar")
                                }
                            }
                        }
                    }
                }

                // Espacio al final para el menú inferior
                item {
                    Spacer(modifier = Modifier.height(80.dp))
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
