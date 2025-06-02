package com.example.ghotels.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import com.example.ghotels.presentation.viewmodel.HomeViewModel
import com.example.ghotels.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.ghotels.presentation.viewmodel.ProfileViewModel
import java.util.*


@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val usuario by profileViewModel.user.collectAsState()
    val editandoPerfil = remember { mutableStateOf(false) }
    val editandoDireccion = remember { mutableStateOf(false) }
    val editandoContacto = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF002A3D)
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp)
            ) {
                item {
                    TopGHotels(title = "Perfil")

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${usuario?.firstName} ${usuario?.lastName}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color(0xFF002B50)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Work, contentDescription = null, tint = Color(0xFF00556E), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(usuario?.role ?: "", color = Color.Gray, fontSize = 13.sp)
                                }
                            }
                            IconButton(onClick = {
                                profileViewModel.logout(navController)
                            }) {
                                Icon(Icons.Default.ExitToApp, contentDescription = "Salir", tint = Color(0xFF00556E))
                            }
                        }
                    }

                    // ---------- DATOS PERSONALES ----------
                    ProfileSectionCard(
                        titulo = "Datos personales",
                        campos = listOf(
                            "Correo electrónico" to usuario?.email.orEmpty(),
                            "Teléfono" to usuario?.phone.orEmpty(),
                            "Estado civil" to usuario?.maritalStatus.orEmpty(),
                            "Nacionalidad" to usuario?.nationality.orEmpty(),
                            "Género" to usuario?.gender.orEmpty()
                        ),
                        editando = editandoPerfil.value,
                        onEditClick = {
                            if (editandoPerfil.value) profileViewModel.saveChanges()
                            editandoPerfil.value = !editandoPerfil.value
                        },
                        onValueChange = { campo, valor -> profileViewModel.updateField(campo, valor) },
                        keys = listOf("email", "phone", "maritalStatus", "nationality", "gender")
                    )

                    // ---------- DIRECCIÓN ----------
                    ProfileSectionCard(
                        titulo = "Dirección",
                        campos = listOf(
                            "Calle" to usuario?.address?.street.orEmpty(),
                            "Ciudad" to usuario?.address?.city.orEmpty(),
                            "Provincia" to usuario?.address?.province.orEmpty(),
                            "Código postal" to usuario?.address?.postalCode.orEmpty(),
                            "País" to usuario?.address?.country.orEmpty()
                        ),
                        editando = editandoDireccion.value,
                        onEditClick = {
                            if (editandoDireccion.value) profileViewModel.saveChanges()
                            editandoDireccion.value = !editandoDireccion.value
                        },
                        onValueChange = { campo, valor -> profileViewModel.updateField(campo, valor) },
                        keys = listOf("addressStreet", "addressCity", "addressProvince", "addressPostalCode", "addressCountry")
                    )

                    // ---------- CONTACTO DE EMERGENCIA ----------
                    val contacto = usuario?.emergencyContact
                    ProfileSectionCard(
                        titulo = "Contacto de emergencia",
                        campos = listOf(
                            "Nombre" to contacto?.name.orEmpty(),
                            "Teléfono" to contacto?.phone.orEmpty(),
                            "Relación" to contacto?.relationship.orEmpty()
                        ),
                        editando = editandoContacto.value,
                        onEditClick = {
                            if (editandoContacto.value) profileViewModel.saveChanges()
                            editandoContacto.value = !editandoContacto.value
                        },
                        onValueChange = { campo, valor ->
                            when (campo) {
                                "name" -> profileViewModel.updateEmergencyContact(valor, contacto?.phone.orEmpty(), contacto?.relationship.orEmpty())
                                "phone" -> profileViewModel.updateEmergencyContact(contacto?.name.orEmpty(), valor, contacto?.relationship.orEmpty())
                                "relationship" -> profileViewModel.updateEmergencyContact(contacto?.name.orEmpty(), contacto?.phone.orEmpty(), valor)
                            }
                        },
                        keys = listOf("name", "phone", "relationship")
                    )

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            MenuGHotels(
                selectedIndex = 4,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ProfileSectionCard(
    titulo: String,
    campos: List<Pair<String, String>>,
    editando: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String, String) -> Unit,
    keys: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF002B50))
            Spacer(modifier = Modifier.height(8.dp))

            campos.forEachIndexed { index, (label, value) ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)) {
                    Text(text = label, fontSize = 13.sp, color = Color(0xFF00556E))
                    if (editando) {
                        OutlinedTextField(
                            value = value,
                            onValueChange = { onValueChange(keys[index], it) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    } else {
                        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEditClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // ✅ centra el botón
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = if (editando) "Guardar" else "Editar",
                    color = Color.White
                )
            }
        }
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        ProfileScreen(navController = navController)
    }
}
