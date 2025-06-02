package com.example.ghotels.presentation.ui.screens.admin.officialholiday

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
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.presentation.navigation.Screen
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel

@Composable
fun AddOfficialHolidayScreen(
    navController: NavController,
    viewModel: OfficialHolidayViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val isValid = name.isNotBlank() && DateUtils.isValidDate(date)

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopGHotels(title = "Nuevo festivo")
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
                            label = { Text("Nombre del festivo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("Fecha (dd/MM/yyyy)") },
                            modifier = Modifier.fillMaxWidth()
                        )
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
                                        viewModel.addHoliday(name, isoDate)
                                        navController.navigate(Screen.OfficialHolidayAdmin.route) {
                                            popUpTo(Screen.OfficialHolidayAdmin.route) { inclusive = true }
                                        }
                                    }
                                },
                                enabled = isValid,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Guardar festivo")
                            }
                        }
                    }
                }
            }

            MenuGHotels(selectedIndex = 5, navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
            FloatingAdminMenu(navController = navController)
        }
    }
}

