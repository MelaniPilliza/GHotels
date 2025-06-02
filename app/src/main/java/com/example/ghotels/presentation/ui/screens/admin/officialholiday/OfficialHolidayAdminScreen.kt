package com.example.ghotels.presentation.ui.screens.admin.officialholiday

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
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
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.domain.model.OfficialHoliday
import com.example.ghotels.presentation.navigation.Screen


@Composable
fun OfficialHolidayAdminScreen(
    navController: NavController,
    viewModel: OfficialHolidayViewModel = koinViewModel()
) {
    val holidays by viewModel.holidays.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHolidays()
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
                    TopGHotels(title = "Festivos")
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "+ Añadir festivo",
                            color = Color(0xFFB3E5FC),
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.AddOfficialHoliday.route)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                when {
                    loading -> item { CircularProgressIndicator(color = Color.White) }
                    error != null -> item {
                        Text(text = error ?: "Error", color = Color.Red, modifier = Modifier.padding(16.dp))
                    }
                    holidays.isEmpty() -> item {
                        Text(text = "No hay festivos registrados", color = Color.White, modifier = Modifier.padding(16.dp))
                    }
                    else -> items(holidays) { holiday ->
                        Card(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFF00556E))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(holiday.name, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.Black)
                                    Text(DateUtils.fromIsoToEuropean(holiday.date) ?: holiday.date, fontSize = 14.sp, color = Color.Gray)
                                }
                                Row(horizontalArrangement = Arrangement.End) {
                                    IconButton(onClick = {
                                        navController.navigate(Screen.UpdateOfficialHoliday.createRoute(holiday.id ?: 0L))
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF2979FF))
                                    }
                                    IconButton(onClick = {
                                        viewModel.deleteHoliday(holiday.id)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }

            MenuGHotels(selectedIndex = 5, navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
            FloatingAdminMenu(navController = navController)
        }
    }
}