package com.example.ghotels.presentation.ui.screens.admin.permissionrequest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.ghotels.domain.model.PermissionRequest
import com.example.ghotels.domain.model.PermissionType
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.presentation.viewmodel.PermissionRequestViewModel
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun PermissionRequestAdminScreen(
    navController: NavController,
    viewModel: PermissionRequestViewModel = koinViewModel()
) {
    val requests by viewModel.requests
    val loading by viewModel.loading
    val error by viewModel.error

    LaunchedEffect(Unit) {
        viewModel.loadAllRequests()
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
                    TopGHotels(title = "Solicitudes de permisos")
                    Spacer(modifier = Modifier.height(16.dp))
                }

                when {
                    loading -> item {
                        CircularProgressIndicator(color = Color.White)
                    }

                    error != null -> item {
                        Text(
                            text = error ?: "Error desconocido",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    requests.isEmpty() -> item {
                        Text(
                            text = "No hay solicitudes pendientes",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    else -> items(requests) { req ->
                        PermissionRequestCard(
                            request = req,
                            onApprove = {
                                if (req.id != null && req.employeeId != null) {
                                    viewModel.approveRequest(req.id, req.employeeId)
                                }
                            },
                            onReject = {
                                if (req.id != null) {
                                    viewModel.rejectRequest(req.id)
                                }
                            }
                        )
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


@Composable
fun PermissionRequestCard(
    request: PermissionRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Empleado ID: ${request.employeeId}", fontWeight = FontWeight.Bold)
            Text("Tipo: ${request.permissionTypeId}")
            Text("Desde: ${request.startDate}")
            Text("Hasta: ${request.endDate}")
            Text(
                text = "Estado: ${request.status}",
                color = when (request.status) {
                    "APPROVED" -> Color(0xFF2E7D32)
                    "REJECTED" -> Color.Red
                    else -> Color.Gray
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (request.status == "PENDING") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onReject,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Rechazar")
                    }
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E))
                    ) {
                        Text("Aprobar", color = Color.White)
                    }
                }
            }
        }
    }
}
