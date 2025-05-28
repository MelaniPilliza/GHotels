package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import com.example.ghotels.presentation.viewmodel.OfficialHolidayViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ghotels.domain.model.OfficialHoliday
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel(),
    officialHolidayViewModel: OfficialHolidayViewModel = koinViewModel()
) {
    val user by homeViewModel.user.collectAsState()
    val holidays by officialHolidayViewModel.holidays.collectAsState()
    val firstName = user?.firstName ?: ""
    val fullName = "${user?.firstName ?: ""} ${user?.lastName ?: ""}"
    val role = user?.role ?: ""

    LaunchedEffect(Unit) {
        officialHolidayViewModel.loadHolidays()
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
                    TopGHotels(title = "Hola $firstName")
                    Spacer(modifier = Modifier.height(20.dp))
                    ProfileCard(fullName = fullName, role = role, modifier = Modifier.fillMaxWidth(0.9f))
                    Spacer(modifier = Modifier.height(20.dp))
                    HolidayCard(holidays = holidays)
                    Spacer(modifier = Modifier.height(20.dp))
                    AbsenceCard(navController = navController)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 0,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            if (role == "ADMIN" || role == "RRHH") {
                FloatingAdminMenu(navController = navController)
            }
        }
    }
}

@Composable
fun ProfileCard(fullName: String, role: String, modifier: Modifier = Modifier) {
    val parts = fullName.split(" ")
    val initials = buildString {
        append(parts.getOrNull(0)?.firstOrNull() ?: "")
        append(parts.getOrNull(1)?.firstOrNull() ?: "")
    }.uppercase()

    Card(
        modifier = modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFBC02D)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = fullName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = role,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HolidayCard(holidays: List<OfficialHoliday>) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val locale = Locale("es", "ES")

    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Festivos", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text("Ver todos >", fontSize = 13.sp, color = Color(0xFF003366))
            }

            Spacer(modifier = Modifier.height(12.dp))

            holidays.sortedBy { it.date }.take(4).forEach { holiday ->
                val date = inputFormat.parse(holiday.date)
                val calendar = Calendar.getInstance().apply { time = date!! }

                val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)?.uppercase() ?: ""
                val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .width(40.dp)
                            .background(Color(0xFFD32F2F), shape = RoundedCornerShape(4.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(month, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                        Text(day, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(holiday.name, fontWeight = FontWeight.SemiBold)
                        Text("Día no laborable", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun AbsenceCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Próximas ausencias",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0x1AFF0000), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = null,
                        tint = Color(0xFF003366)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Planifica tus próximas ausencias",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Haz click en 'Solicitar ausencia'",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate("permissionrequest") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text("Solicitar ausencia", color = Color.Black)
            }
        }
    }
}






@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}
