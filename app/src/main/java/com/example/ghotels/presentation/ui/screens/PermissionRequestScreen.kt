package com.example.ghotels.presentation.ui.screens


import android.widget.Toast
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
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.domain.model.PermissionType
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.presentation.viewmodel.PermissionRequestViewModel
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionRequestScreen(
    navController: NavController,
    permissionTypeViewModel: PermissionTypeViewModel = koinViewModel(),
    permissionRequestViewModel: PermissionRequestViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val permissionTypes by permissionTypeViewModel.permissionTypes.collectAsState()
    val user by homeViewModel.user.collectAsState()
    val employeeId = user?.id ?: return

    val balances by permissionRequestViewModel.balances.collectAsState()
    var selectedType by remember { mutableStateOf<PermissionType?>(null) }
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // CARGAR SALDOS
    LaunchedEffect(employeeId) {
        permissionRequestViewModel.loadBalances(employeeId)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                item {
                    TopGHotels(title = "Solicitar ausencia")
                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {

                            Text("¿Qué tipo de ausencia?", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Box {
                                OutlinedTextField(
                                    value = selectedType?.name ?: "Seleccionar...",
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Tipo de permiso") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.clickable { expanded = true }
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    permissionTypes.forEach { tipo ->
                                        DropdownMenuItem(
                                            text = { Text(tipo.name) },
                                            onClick = {
                                                selectedType = tipo
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // MOSTRAR SALDO
                            selectedType?.let { tipo ->
                                val balanceText = balances.find { it.type == tipo.name }?.balance ?: "Cargando..."
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Saldo disponible: $balanceText",
                                    color = Color(0xFF00556E),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Desde - Hasta", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = fromDate,
                                    onValueChange = { fromDate = it },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("dd/MM/yyyy") },
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = toDate,
                                    onValueChange = { toDate = it },
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text("dd/MM/yyyy") },
                                    singleLine = true
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Comentario", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = comment,
                                onValueChange = { comment = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                placeholder = { Text("Añadir comentario...") }
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                OutlinedButton(
                                    onClick = { navController.popBackStack() },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                                ) {
                                    Text("Cancelar")
                                }

                                Button(
                                    onClick = {
                                        if (!DateUtils.isValidDate(fromDate) || !DateUtils.isValidDate(toDate)) {
                                            Toast.makeText(
                                                context,
                                                "Formato de fecha incorrecto. Usa dd/MM/yyyy",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            return@Button
                                        }

                                        selectedType?.let {
                                            coroutineScope.launch {
                                                val success = permissionRequestViewModel.createRequest(
                                                    fromDate = fromDate,
                                                    toDate = toDate,
                                                    comment = comment,
                                                    permissionTypeId = it.id!!,
                                                    employeeId = employeeId
                                                )
                                                if (success) {
                                                    navController.popBackStack()
                                                }
                                            }
                                        }
                                    },
                                    enabled = selectedType != null && fromDate.isNotBlank() && toDate.isNotBlank(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E))
                                ) {
                                    Text("Solicitar", color = Color.White)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 1,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


