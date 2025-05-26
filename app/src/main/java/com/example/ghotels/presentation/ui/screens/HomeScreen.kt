package com.example.ghotels.presentation.ui.screens

import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.data.model.FestivoDto
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import com.example.ghotels.presentation.viewmodel.FestivoViewModel
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel(),
    festivoViewModel: FestivoViewModel = koinViewModel()
) {
    val usuario by homeViewModel.usuario.collectAsState()
    val festivos by festivoViewModel.festivos.collectAsState()
    val nombre = usuario?.nombre ?: ""
    val nombreCompleto = "${usuario?.nombre ?: ""} ${usuario?.apellidos ?: ""}"
    val rol = usuario?.rol ?: ""

    LaunchedEffect(Unit) {
        festivoViewModel.cargarFestivos()
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
                    TopGHotels(titulo = "Hola $nombre")
                    Spacer(modifier = Modifier.height(20.dp))
                    CardPerfil(nombreCompleto = nombreCompleto, rol = rol, modifier = Modifier.fillMaxWidth(0.9f))
                    Spacer(modifier = Modifier.height(20.dp))
                    CardFestivos(festivos = festivos)
                    Spacer(modifier = Modifier.height(20.dp))
                    CardAusencias()
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 0,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter) // 👈 NECESARIO
            )



            if (rol == "ADMIN" || rol == "RRHH") {
                FloatingAdminMenu(navController = navController


                )
            }
        }
    }
}

@Composable
fun CardPerfil(nombreCompleto: String, rol: String, modifier: Modifier = Modifier) {
    // Extraemos iniciales: 1ª del nombre + 1ª del primer apellido
    val partes = nombreCompleto.split(" ")
    val iniciales = buildString {
        append(partes.getOrNull(0)?.firstOrNull() ?: "")
        append(partes.getOrNull(1)?.firstOrNull() ?: "")
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
                    text = iniciales,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = nombreCompleto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = rol,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun CardFestivos(festivos: List<FestivoDto>) {
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
                Text("Ver todas >", fontSize = 13.sp, color = Color(0xFF003366))
            }

            Spacer(modifier = Modifier.height(12.dp))

            festivos.sortedBy { it.fecha }.take(3).forEach { festivo ->
                val date = inputFormat.parse(festivo.fecha)
                val calendar = Calendar.getInstance().apply { time = date!! }

                val mes = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)?.uppercase() ?: ""
                val dia = calendar.get(Calendar.DAY_OF_MONTH).toString()

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
                        Text(mes, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                        Text(dia, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(festivo.nombre, fontWeight = FontWeight.SemiBold)
                        Text("Madrid", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}


@Composable
fun CardAusencias() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f),
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

            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0x1AFF0000), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Work, // Puedes cambiarlo por un ícono de maleta si tienes uno personalizado
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
                        text = "Haz click en 'Solicitar días libres'",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* TODO */ },
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
