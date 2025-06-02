package com.example.ghotels.presentation.ui.screens.admin.permissiontype

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Work
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
import com.example.ghotels.data.model.PermissionTypeDto
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import com.example.ghotels.domain.model.PermissionType
import com.example.ghotels.presentation.navigation.Screen


@Composable
fun PermissionTypeAdminScreen(
    navController: NavController,
    viewModel: PermissionTypeViewModel = koinViewModel()
) {
    val permissionTypes by viewModel.permissionTypes.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading = permissionTypes.isEmpty() && error == null

    LaunchedEffect(Unit) {
        viewModel.loadPermissionTypes()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopGHotels(title = "Tipos de Permiso")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "+ Añadir tipo",
                            color = Color(0xFFB3E5FC),
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.AddPermissionType.route)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                when {
                    loading -> item {
                        CircularProgressIndicator(color = Color.White)
                    }
                    error != null -> item {
                        Text(
                            text = error ?: "Error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    permissionTypes.isEmpty() -> item {
                        Text(
                            text = "No hay tipos de permiso",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> items(permissionTypes) { type ->
                        Card(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = "Tipo",
                                    tint = Color(0xFF00556E)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = type.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp,
                                        color = Color.Black
                                    )
                                    if (type.description.isNotBlank()) {
                                        Text(
                                            text = type.description,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (type.unlimited) "Ilimitado" else "Días: ${type.annualAvailableDays}",
                                            fontSize = 13.sp,
                                            color = Color.DarkGray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (type.requiresApproval) "Requiere aprobación" else "Sin aprobación",
                                            fontSize = 13.sp,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier.weight(0.3f),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(
                                                Screen.UpdatePermissionType.createRoute(type.id ?: 0L)
                                            )
                                        },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar Tipo",
                                            tint = Color(0xFF2979FF)
                                        )
                                    }
                                    IconButton(onClick = {
                                        viewModel.deletePermissionType(type.id ?: 0L)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar Tipo",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
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