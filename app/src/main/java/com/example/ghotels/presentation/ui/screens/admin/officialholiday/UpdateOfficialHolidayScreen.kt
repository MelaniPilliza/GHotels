package com.example.ghotels.presentation.ui.screens.admin.officialholiday

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.presentation.navigation.Screen
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel
import com.example.ghotels.presentation.viewmodel.RoleViewModel

@Composable
fun UpdateOfficialHolidayScreen(
    navController: NavController,
    holidayId: Long?,
    viewModel: OfficialHolidayViewModel = koinViewModel()
) {
    val holidays by viewModel.holidays.collectAsState()
    val currentHoliday = remember(holidays) { holidays.find { it.id == holidayId } }

    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val isValid = name.isNotBlank() && DateUtils.isValidDate(date)

    LaunchedEffect(currentHoliday) {
        currentHoliday?.let {
            name = it.name
            date = DateUtils.fromIsoToEuropean(it.date) ?: ""
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    TopGHotels(title = "Editar festivo")
                    Spacer(modifier = Modifier.height(16.dp))
                }


                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Nombre del festivo
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Nombre del festivo") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Fecha en formato dd/MM/yyyy, marcando error si no es válido
                            OutlinedTextField(
                                value = date,
                                onValueChange = { date = it },
                                label = { Text("Fecha (dd/MM/yyyy)") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = date.isNotBlank() && !DateUtils.isValidDate(date)
                            )

                            // Mensaje de error bajo el campo fecha
                            if (date.isNotBlank() && !DateUtils.isValidDate(date)) {
                                Text(
                                    text = "Formato inválido. Usa dd/MM/yyyy.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))


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
                                        val isoDate = DateUtils.toIsoFormat(date)
                                        if (isoDate != null) {
                                            viewModel.updateHoliday(holidayId, name.trim(), isoDate)
                                            navController.popBackStack()
                                        }
                                    },
                                    enabled = isValid,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Guardar cambios")
                                }
                            }
                        }
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            MenuGHotels(selectedIndex = 5, navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
            FloatingAdminMenu(navController = navController)
        }
    }
}
