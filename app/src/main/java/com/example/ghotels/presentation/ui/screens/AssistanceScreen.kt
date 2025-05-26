package com.example.ghotels.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ghotels.ui.theme.AppTheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Pause
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
import com.example.ghotels.presentation.viewmodel.AsistenciaViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AssistanceScreen(
    navController: NavController,
    asistenciaViewModel: AsistenciaViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val usuario by homeViewModel.usuario.collectAsState()
    val empleadoId = usuario?.id ?: return
    val horasLaboralesDiarias = usuario?.horasLaboralesDiarias ?: 8

    val haEntrado by asistenciaViewModel.haEntrado.collectAsState()
    val motivo by asistenciaViewModel.motivo.collectAsState()
    val tiempo by asistenciaViewModel.tiempoActual.collectAsState()
    val mostrarAdvertencia by asistenciaViewModel.mostrarAdvertencia.collectAsState()
    val esperandoMotivo by asistenciaViewModel.esperandoMotivo.collectAsState()

    val (horas, minutos, segundos) = tiempo
    val jornadaCumplida = (horas * 60 + minutos) >= horasLaboralesDiarias * 60

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
                    TopGHotels(titulo = "Control de asistencia")
                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                            Text("Hoy, $fechaActual", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Tiempo trabajado", fontWeight = FontWeight.Medium)
                            Text(
                                String.format("%02dh %02dm %02ds", horas, minutos, segundos),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            val progreso = (horas * 60 + minutos).toFloat() / (horasLaboralesDiarias * 60)
                            LinearProgressIndicator(
                                progress = progreso.coerceIn(0f, 1f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = Color(0xFF00556E)
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Esta semana: ${horas}h ${minutos}m / ${horasLaboralesDiarias * 5}h", fontSize = 12.sp)

                            Spacer(modifier = Modifier.height(16.dp))

                            // ⏯ Botón Entrar
                            if (!haEntrado && !esperandoMotivo) {
                                Button(
                                    onClick = { asistenciaViewModel.registrarEntrada(empleadoId) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E)),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Entrar", color = Color.White)
                                }
                            }

                            // ⚠️ Motivo por incumplimiento
                            if (esperandoMotivo) {
                                Text(
                                    "No has cumplido tu jornada laboral. Indica el motivo.",
                                    color = Color.Red,
                                    fontSize = 13.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = motivo,
                                    onValueChange = { asistenciaViewModel.setMotivo(it) },
                                    placeholder = { Text("Ej: Médico, incidencia con el transporte ...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    isError = motivo.isBlank()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        asistenciaViewModel.registrarSalida(empleadoId, horasLaboralesDiarias)
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    enabled = motivo.isNotBlank(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E)),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("Enviar", color = Color.White)
                                }
                            }

                            // ⏹ Botón Finalizar normal
                            if (haEntrado && !esperandoMotivo) {
                                OutlinedButton(
                                    onClick = {
                                        asistenciaViewModel.registrarSalida(
                                            idEmpleado = empleadoId,
                                            horasRequeridas = horasLaboralesDiarias
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
        AssistanceScreen(navController = navController)
    }
}
