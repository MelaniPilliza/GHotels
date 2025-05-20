package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghotels.presentation.ui.components.MenuGHotels


@Composable
fun BalanceScreen() {
    val balances = listOf(
        "Vacaciones pagadas" to "Quedan 22 días",
        "Baja médica por enfermedad" to "Ilimitado",
        "Cita médica" to "Ilimitado",
        "Acompañamiento familiar" to "Ilimitado",
        "Mudanza" to "2 días",
        "Permiso maternidad/paternidad" to "116 días",
        "Lactancia" to "Ilimitado"
    )

    val colorMap = mapOf(
        "Vacaciones pagadas" to Color(0xFF4CAF50), // Verde
        "Baja médica por enfermedad" to Color(0xFFB71C1C), // Rojo oscuro
        "Cita médica" to Color(0xFFCCFF90), // Verde claro
        "Acompañamiento familiar" to Color(0xFF00BCD4), // Azul claro
        "Mudanza" to Color(0xFF3E2723), // Marrón
        "Permiso maternidad/paternidad" to Color(0xFFE040FB), // Fucsia
        "Lactancia" to Color(0xFF9C27B0) // Púrpura
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 90.dp)
            ) {
                item {
                    // Cabecera
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.White)
                            .padding(start = 24.dp, top = 62.dp)
                    ) {
                        Text(
                            text = "Saldos",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tarjeta
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(vertical = 16.dp)) {
                            balances.forEachIndexed { index, (tipo, dias) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Punto de color
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .background(
                                                    colorMap[tipo] ?: Color.Gray,
                                                    shape = CircleShape
                                                )
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(text = tipo, fontSize = 16.sp, color = Color.Black)
                                    }

                                    Text(text = dias, fontSize = 14.sp, color = Color.Gray)
                                }

                                if (index != balances.lastIndex) {
                                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 1,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BalanceScreenPreview() {
    BalanceScreen()
}