package com.example.ghotels.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ghotels.ui.theme.AppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import com.example.ghotels.presentation.viewmodel.AttendanceViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AttendanceScreen(
    navController: NavController,
    attendanceViewModel: AttendanceViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    // Obtenemos el usuario actual del ViewModel
    val user by homeViewModel.user.collectAsState()
    val employeeId = user?.id ?: return
    val dailyWorkingHours = user?.dailyWorkingHours ?: 8

    //Verifico si hay asistecia existente
    LaunchedEffect(employeeId) {
        attendanceViewModel.checkOpenAttendanceOnStartup(employeeId)
    }


    val hasCheckedIn by attendanceViewModel.hasCheckedIn.collectAsState()
    val reason by attendanceViewModel.reason.collectAsState()
    val workedTime by attendanceViewModel.workedTime.collectAsState()
    val waitingForReason by attendanceViewModel.waitingForReason.collectAsState()


    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF002B50)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 90.dp)
            ) {
                item {
                    TopGHotels(title = "Control de asistencia")
                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally // ⬅️ Centra texto y botones dentro del card
                        )  {
                            val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                            Text("Hoy, $today", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Tiempo trabajado", fontWeight = FontWeight.Medium)
                            Text(
                                workedTime,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Jornada laboral: ${dailyWorkingHours}h", fontSize = 12.sp)

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botón de ENTRAR (inicio de jornada)
                            if (!hasCheckedIn && !waitingForReason) {
                                Button(
                                    onClick = { attendanceViewModel.registerEntry(employeeId) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E)),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Entrar", color = Color.White)
                                }
                            }

                            // Motivo si no se cumple la jornada
                            if (waitingForReason) {
                                Text(
                                    "No has cumplido tu jornada laboral. Indica el motivo.",
                                    color = Color.Red,
                                    fontSize = 13.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = reason,
                                    onValueChange = { attendanceViewModel.setReason(it) },
                                    placeholder = { Text("Ej: Médico, incidencia con el transporte ...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    isError = reason.isBlank()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        attendanceViewModel.registerExit(employeeId, dailyWorkingHours)
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    enabled = reason.isNotBlank(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E)),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("Enviar", color = Color.White)
                                }
                            }

                            // Botón de FINALIZAR si ha entrado y cumplido jornada
                            if (hasCheckedIn && !waitingForReason) {
                                OutlinedButton(
                                    onClick = {
                                        attendanceViewModel.registerExit(
                                            employeeId = employeeId,
                                            requiredHours = dailyWorkingHours
                                        )
                                    },
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Icon(Icons.Default.Stop, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Finalizar")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        // Menú inferior personalizado
        MenuGHotels(
            selectedIndex = 2,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssistanceScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        AttendanceScreen(navController = navController)
    }
}
