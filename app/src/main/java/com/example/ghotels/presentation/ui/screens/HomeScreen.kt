package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.ui.theme.AppTheme

@Composable
fun HomeScreen() {
    val freeDays = listOf("JUEVES SANTO", "VIERNES SANTO")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF202938)
    ) {
        Box {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp) // deja espacio para el menú
            ) {
                item {
                    // Cabecera
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(Color.White)
                            .padding(start = 24.dp, top = 62.dp, end = 24.dp)
                    ) {
                        Column {
                            Text(
                                text = "Hola, Melani",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "01/04/2025",
                                fontSize = 18.sp,
                                color = Color(0xFF202938)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tarjeta de días libres
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Días libres",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            freeDays.forEach {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = it, fontWeight = FontWeight.Bold)
                                    Text(text = "Festivo", color = Color.Gray)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { /* TODO */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF202938),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 10.dp)
                            ) {
                                Text(text = "Solicitar días libres")
                            }
                        }
                    }
                }
            }

            // Menú fijo inferior
            MenuGHotels(
                selectedIndex = 0,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme  {
        HomeScreen()
    }
}