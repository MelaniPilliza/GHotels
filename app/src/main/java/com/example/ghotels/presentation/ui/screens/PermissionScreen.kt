package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.viewmodel.PermissionViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import com.example.ghotels.data.model.TipoPermisoDto


@Composable
fun PermissionScreen(
    navController: NavController,
    permissionViewModel: PermissionViewModel = koinViewModel()
) {
    val tiposPermiso by permissionViewModel.tiposPermiso.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF002B50) // azul fondo
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopGHotels(titulo = "Permisos")

                    Spacer(modifier = Modifier.height(20.dp))

                    CardPermisos(tiposPermiso = tiposPermiso)

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // Menú inferior
            MenuGHotels(
                selectedIndex = 1,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter) // 👈 NECESARIO
            )


        }
    }
}

@Composable
fun CardPermisos(tiposPermiso: List<TipoPermisoDto>) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            tiposPermiso.forEachIndexed { index, permiso ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(
                                    color = Color(0xFF4CAF50), // Color verde único
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = permiso.nombre, fontSize = 16.sp, color = Color.Black)
                    }

                    val diasTexto = if (permiso.ilimitado) {
                        "Ilimitado"
                    } else {
                        "Quedan ${permiso.diasDisponiblesAnuales ?: 0} días"
                    }

                    Text(text = diasTexto, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                if (index != tiposPermiso.lastIndex) {
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        PermissionScreen(navController = navController)
    }
}