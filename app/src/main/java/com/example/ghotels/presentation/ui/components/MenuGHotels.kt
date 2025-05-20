package com.example.ghotels.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuGHotels(
    selectedIndex: Int = 0,
    modifier: Modifier = Modifier
) {
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.CalendarToday,
        Icons.Default.AccessTime,
        Icons.Default.Group,
        Icons.Default.Person
    )

    val labels = listOf("Inicio", "Vacaciones", "Asistencias", "Empleados", "Perfil")

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEachIndexed { index, label ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = label,
                        tint = if (selectedIndex == index) Color(0xFF0576CE) else Color.Black
                    )
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        color = if (selectedIndex == index) Color(0xFF0576CE) else Color.Black
                    )
                }
            }
        }
    }
}

