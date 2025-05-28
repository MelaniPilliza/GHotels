package com.example.ghotels.presentation.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ghotels.presentation.ui.components.FloatingAdminMenu
import com.example.ghotels.presentation.ui.components.MenuGHotels
import com.example.ghotels.presentation.ui.components.TopGHotels
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.ghotels.data.model.AddressDto
import com.example.ghotels.data.model.RegisterEmployeeDto
import com.example.ghotels.domain.model.Address
import com.example.ghotels.domain.model.Employee
import com.example.ghotels.presentation.viewmodel.RegisterEmployeeViewModel

@Composable
fun AddEmployeeScreen(
    navController: NavController,
    viewModel: RegisterEmployeeViewModel = koinViewModel()
) {
    // Required fields
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var movil by remember { mutableStateOf("") }
    var numeroSS by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("2025-05-26") }
    var tipoContrato by remember { mutableStateOf("") }
    var horasDiarias by remember { mutableStateOf("") }

    // Optional fields
    var nacionalidad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var estadoCivil by remember { mutableStateOf("") }
    var numeroHijos by remember { mutableStateOf("") }
    var discapacidad by remember { mutableStateOf(false) }

    // Address
    var calle by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }

    val allRequiredFilled = nombre.isNotBlank() && apellidos.isNotBlank() && dni.isNotBlank() &&
            mail.isNotBlank() && password.isNotBlank() && movil.isNotBlank() &&
            numeroSS.isNotBlank() && fechaNacimiento.isNotBlank() &&
            fechaIngreso.isNotBlank() && tipoContrato.isNotBlank() && horasDiarias.isNotBlank()

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002B50)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopGHotels(title = "Nuevo Empleado")

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    LazyColumn {
                        item {
                            // DATOS PERSONALES
                            Text("Datos personales", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF002B50))
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = apellidos, onValueChange = { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = dni, onValueChange = { dni = it }, label = { Text("DNI") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = mail, onValueChange = { mail = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = movil, onValueChange = { movil = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = numeroSS, onValueChange = { numeroSS = it }, label = { Text("NSS") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, label = { Text("Fecha de nacimiento") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = fechaIngreso, onValueChange = { fechaIngreso = it }, label = { Text("Fecha de ingreso") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = tipoContrato, onValueChange = { tipoContrato = it }, label = { Text("Tipo de contrato") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = horasDiarias, onValueChange = { horasDiarias = it }, label = { Text("Horas laborales diarias") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())

                            Spacer(modifier = Modifier.height(16.dp))

                            // DIRECCIÓN
                            Text("Dirección", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF002B50))
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = calle, onValueChange = { calle = it }, label = { Text("Calle") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = codigoPostal, onValueChange = { codigoPostal = it }, label = { Text("Código Postal") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = ciudad, onValueChange = { ciudad = it }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = provincia, onValueChange = { provincia = it }, label = { Text("Provincia") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = pais, onValueChange = { pais = it }, label = { Text("País") }, modifier = Modifier.fillMaxWidth())

                            Spacer(modifier = Modifier.height(16.dp))

                            // OTROS DATOS
                            Text("Otros datos", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF002B50))
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(value = nacionalidad, onValueChange = { nacionalidad = it }, label = { Text("Nacionalidad") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = genero, onValueChange = { genero = it }, label = { Text("Género") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = estadoCivil, onValueChange = { estadoCivil = it }, label = { Text("Estado Civil") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = numeroHijos, onValueChange = { numeroHijos = it }, label = { Text("Nº de hijos") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = discapacidad, onCheckedChange = { discapacidad = it })
                                Text("Discapacidad", modifier = Modifier.padding(start = 8.dp))
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        // GUARDAR
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = {
                                        val employee = Employee(
                                            id = null,
                                            dni = dni,
                                            firstName = nombre,
                                            lastName = apellidos,
                                            email = mail,
                                            password = password,
                                            phone = movil,
                                            socialSecurityNumber = numeroSS,
                                            birthDate = fechaNacimiento,
                                            entryDate = fechaIngreso, // ✅ nombre correcto

                                            contractType = tipoContrato,
                                            dailyWorkingHours = horasDiarias.toIntOrNull() ?: 8,

                                            role = "EMPLEADO",
                                            department = "Operaciones",
                                            supervisorName = null,
                                            supervisorRole = null,

                                            address = Address(
                                                street = calle,
                                                postalCode = codigoPostal,
                                                city = ciudad,
                                                province = provincia,
                                                country = pais
                                            ),
                                            nationality = nacionalidad.ifBlank { null },
                                            gender = genero.ifBlank { null },
                                            maritalStatus = estadoCivil.ifBlank { null },
                                            numberOfChildren = numeroHijos.toIntOrNull() ?: 0,
                                            disability = discapacidad
                                        )
                                        viewModel.registerEmployee(employee)
                                    },
                                    enabled = allRequiredFilled,
                                    modifier = Modifier.align(Alignment.Center),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E))
                                ) {
                                    Text("Guardar", color = Color.White)
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }
            }

            MenuGHotels(selectedIndex = 5, navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
            FloatingAdminMenu(navController = navController)
        }
    }
}