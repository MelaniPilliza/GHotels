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
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel

@Composable
fun AddOfficialHolidayScreen(
    navController: NavController,
    viewModel: OfficialHolidayViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val isValid = name.isNotBlank() && date.isNotBlank()

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
                TopGHotels(title = "New official holiday")
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
                            label = { Text("Holiday name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("Date (YYYY-MM-DD)") },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("e.g. 2025-08-15") }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                viewModel.addHoliday(name, date)
                                navController.popBackStack()
                            },
                            enabled = isValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Save holiday")
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
