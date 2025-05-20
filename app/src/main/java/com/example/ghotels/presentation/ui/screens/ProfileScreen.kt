package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghotels.presentation.ui.components.MenuGHotels


@Composable
fun ProfileScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                item {
                    // Encabezado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Melani Pilliza",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Work,
                                    contentDescription = "Rol",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Office Cleaner", color = Color.Gray)
                            }
                        }
                        IconButton(onClick = { /* TODO: ajustes */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Ajustes",
                                tint = Color.Gray
                            )
                        }
                    }

                    // Datos personales
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        ProfileField("Nombre (legal)", "Melani Alexandra")
                        ProfileField("Apellido (legal)", "Pilliza Guacapiña")
                        ProfileField("Correo electrónico", "melanipillizag@gmail.com")
                        ProfileField("Oficina", "Madrid")
                        ProfileField("Supervisor", "Teresa ")
                        ProfileField("Departamento", "Programadora")
                        ProfileField("Birth date", "01/06/2001")
                    }
                }
            }

            // Menú inferior
            MenuGHotels(
                selectedIndex = 4,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
