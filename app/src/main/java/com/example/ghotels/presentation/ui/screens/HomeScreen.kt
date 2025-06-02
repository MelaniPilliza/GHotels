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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002A3D)) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopGHotels(title = "Hola $firstName")
                    Spacer(modifier = Modifier.height(24.dp))
                    ProfileCard(fullName = fullName, role = role)
                    Spacer(modifier = Modifier.height(24.dp))
                    HolidayCard(holidays)
                    Spacer(modifier = Modifier.height(24.dp))
                    AbsenceCard(navController)
                }
            }

            if (role == "ADMIN" || role == "RRHH") {
                FloatingAdminMenu(navController = navController)
            }

            MenuGHotels(
                selectedIndex = 0,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ProfileCard(fullName: String, role: String, modifier: Modifier = Modifier) {
    val initials = fullName.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .uppercase()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF061E33)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFF1F3C5E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3F2DB6)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = initials, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = fullName, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.White)
                Text(text = role, fontSize = 14.sp, color = Color(0xFFB0BEC5))
            }
        }
    }
}

@Composable
fun HolidayCard(holidays: List<OfficialHoliday>) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val locale = Locale("es", "ES")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF061E33)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFF1F3C5E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Festivos", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.White)
                Text("Ver todos >", fontSize = 15.sp, color = Color(0xFF4FC3F7))
            }

            Spacer(modifier = Modifier.height(12.dp))

            holidays.sortedBy { it.date }.take(4).forEach { holiday ->
                val date = inputFormat.parse(holiday.date)
                val calendar = Calendar.getInstance().apply { time = date!! }
                val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)?.uppercase() ?: ""
                val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .width(40.dp)
                            .background(Color(0xFFEF5350), shape = RoundedCornerShape(6.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(month, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(day, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(holiday.name, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.White)
                        Text("Día no laborable", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun AbsenceCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF061E33)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFF1F3C5E))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Próximas ausencias",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF4FC3F7), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Planifica tus próximas ausencias",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Haz clic en 'Solicitar ausencia'",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("permissionrequest") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4FC3F7))
            ) {
                Text("Solicitar ausencia", fontSize = 15.sp, color = Color.Black)
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
