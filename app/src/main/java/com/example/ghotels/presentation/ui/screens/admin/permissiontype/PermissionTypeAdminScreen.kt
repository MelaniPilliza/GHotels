package com.example.ghotels.presentation.ui.screens.admin.permissiontype

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
import com.example.ghotels.domain.model.PermissionType


@Composable
fun PermissionTypeAdminScreen(
    navController: NavController,
    viewModel: PermissionTypeViewModel = koinViewModel()
) {
    val permissionTypes by viewModel.permissionTypes.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPermissionTypes()
    }

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
                    TopGHotels(title = "Tipos de permiso")
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "+ Añadir permiso",
                            color = Color(0xFFB3E5FC),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {
                                navController.navigate("addpermission")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (error != null) {
                    item {
                        Text(
                            text = error ?: "Error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                items(permissionTypes) { type ->
                    PermissionTypeCard(
                        type = type,
                        onEditClick = {
                            navController.navigate("editpermiso/${type.id}")
                        }
                    )
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
fun PermissionTypeCard(type: PermissionType, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Tipo de permiso",
                    tint = Color(0xFF00556E),
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = type.name, fontWeight = FontWeight.Bold)
                    Text(
                        text = type.description,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF007AFF))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = if (type.requiresApproval) "Requiere aprobación" else "Sin aprobación",
                    fontSize = 12.sp
                )
                Text(
                    text = if (type.unlimited) "Ilimitado" else "Límite: ${type.annualAvailableDays ?: 0} días",
                    fontSize = 12.sp
                )
            }
        }
    }
}
