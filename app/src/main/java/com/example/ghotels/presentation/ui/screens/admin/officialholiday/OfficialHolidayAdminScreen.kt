package com.example.ghotels.presentation.ui.screens.admin.officialholiday

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
import com.example.ghotels.domain.model.OfficialHoliday


@Composable
fun OfficialHolidayAdminScreen(
    navController: NavController,
    viewModel: OfficialHolidayViewModel = koinViewModel()
) {
    val holidays by viewModel.holidays.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHolidays()
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
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {
                                navController.navigate("addfestivo")
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
                            text = error ?: "Error desconocido",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    holidays.isEmpty() -> item {
                        Text(
                            text = "No hay festivos registrados",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    else -> items(holidays) { holiday ->
                        CardOfficialHoliday(
                            holiday = holiday,
                            onEditClick = {
                                // navController.navigate("editfestivo/${holiday.name}")
                            }
                        )
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

@Composable
fun CardOfficialHoliday(
    holiday: OfficialHoliday,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Holiday",
                tint = Color(0xFF00556E),
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = holiday.name, fontWeight = FontWeight.SemiBold)
                Text(
                    text = holiday.date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF007AFF))
            }
        }
    }
}
