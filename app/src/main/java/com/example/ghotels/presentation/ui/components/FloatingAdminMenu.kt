package com.example.ghotels.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FloatingAdminMenu(
    navController: NavController
) {
    var expandido by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo desenfocado que impide interacción con el fondo
        if (expandido) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCC000000)) // semi-transparente
                    .clickable { expandido = false }
            )
        }

        // Menú flotante
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility (visible = expandido) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    AdminMenuItem("EMPLEADOS") { navController.navigate("staffadmin") }
                    AdminMenuItem("FESTIVOS") { navController.navigate("festivos") }
                    AdminMenuItem("PERMISOS") { navController.navigate("permisos") }
                }
            }

            FloatingActionButton(
                onClick = { expandido = !expandido },
                shape = CircleShape,
                containerColor = if (expandido) Color(0xFF002B50) else Color(0xFF007AFF),
                elevation = FloatingActionButtonDefaults.elevation(6.dp)
            ) {
                Icon(
                    imageVector = if (expandido) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AdminMenuItem(texto: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}


