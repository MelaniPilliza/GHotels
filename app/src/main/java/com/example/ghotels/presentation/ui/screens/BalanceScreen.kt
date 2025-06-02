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
import com.example.ghotels.presentation.viewmodel.PermissionTypeViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import com.example.ghotels.data.model.PermissionBalanceDto
import com.example.ghotels.domain.model.PermissionType
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.presentation.viewmodel.PermissionRequestViewModel


@Composable
fun BalanceScreen(
    navController: NavController,
    permissionViewModel: PermissionTypeViewModel = koinViewModel(),
    permissionRequestViewModel: PermissionRequestViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val permissionTypes by permissionViewModel.permissionTypes.collectAsState()
    val balances by permissionRequestViewModel.balances.collectAsState()
    val user by homeViewModel.user.collectAsState()

    val employeeId = user?.id ?: return

    // CARGAR SALDOS
    LaunchedEffect(employeeId) {
        permissionRequestViewModel.loadBalances(employeeId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF002A3D)
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TopGHotels(title = "Permisos")

                    Spacer(modifier = Modifier.height(20.dp))

                    PermissionTypeCard(permissionTypes, balances)

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 1,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


@Composable
fun PermissionTypeCard(
    permissionTypes: List<PermissionType>,
    balances: List<PermissionBalanceDto>
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            permissionTypes.forEachIndexed { index, type ->
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
                                    color = Color(0xFF4CAF50),
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = type.name, fontSize = 16.sp, color = Color.Black)
                    }

                    val balanceText = balances.find { it.type == type.name }?.balance ?: "Cargando..."

                    Text(
                        text = balanceText,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (index != permissionTypes.lastIndex) {
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                }
            }
        }
    }
}





