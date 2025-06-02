package com.example.ghotels.presentation.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.ghotels.data.model.UpdateEmployeeDto
import com.example.ghotels.data.utils.DateUtils
import com.example.ghotels.domain.model.Address
import com.example.ghotels.domain.model.Department
import com.example.ghotels.domain.model.Employee
import com.example.ghotels.domain.model.Role
import com.example.ghotels.presentation.viewmodel.DepartmentViewModel
import com.example.ghotels.presentation.viewmodel.RegisterEmployeeViewModel
import com.example.ghotels.presentation.viewmodel.RoleViewModel
import com.example.ghotels.presentation.viewmodel.StaffViewModel



@Composable
fun UpdateEmployeeScreen(
    navController: NavController,
    staffViewModel: StaffViewModel = koinViewModel(),
    roleViewModel: RoleViewModel = koinViewModel(),
    departmentViewModel: DepartmentViewModel = koinViewModel(),
    employeeId: Long
) {
    val roles by roleViewModel.roles.collectAsState()
    val departments by departmentViewModel.departments.collectAsState()
    val selectedEmployee by staffViewModel.selectedEmployee.collectAsState()
    val updateSuccess by staffViewModel.updateSuccess.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var movil by remember { mutableStateOf("") }
    var numeroSS by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("") }
    var tipoContrato by remember { mutableStateOf("") }
    var horasDiarias by remember { mutableStateOf("") }

    var selectedRole by remember { mutableStateOf<Role?>(null) }
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var expandedRole by remember { mutableStateOf(false) }
    var expandedDept by remember { mutableStateOf(false) }

    LaunchedEffect(employeeId) {
        staffViewModel.loadEmployeeById(employeeId)
    }

    LaunchedEffect(selectedEmployee) {
        selectedEmployee?.let { emp ->
            nombre = emp.firstName
            apellidos = emp.lastName
            dni = emp.dni
            mail = emp.email
            movil = emp.phone
            numeroSS = emp.socialSecurityNumber
            fechaNacimiento = DateUtils.fromIsoToEuropean(emp.birthDate) ?: ""
            fechaIngreso = DateUtils.fromIsoToEuropean(emp.entryDate ?: "") ?: ""
            tipoContrato = emp.contractType
            horasDiarias = emp.dailyWorkingHours.toString()
            selectedRole = roles.find { it.name == emp.role }
            selectedDepartment = departments.find { it.name == emp.department }
        }
    }

    LaunchedEffect(roles) {
        selectedEmployee?.role?.let { roleName ->
            selectedRole = roles.find { it.name == roleName }
        }
    }
    LaunchedEffect(departments) {
        selectedEmployee?.department?.let { deptName ->
            selectedDepartment = departments.find { it.name == deptName }
        }
    }

    if (selectedEmployee == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    if (updateSuccess == true) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

    val allRequiredFilled = nombre.isNotBlank() &&
            apellidos.isNotBlank() &&
            dni.isNotBlank() &&
            mail.isNotBlank() &&
            movil.isNotBlank() &&
            numeroSS.isNotBlank() &&
            fechaNacimiento.isNotBlank() &&
            fechaIngreso.isNotBlank() &&
            tipoContrato.isNotBlank() &&
            horasDiarias.isNotBlank() &&
            selectedRole != null &&
            selectedDepartment != null

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002B50)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopGHotels(title = "Editar Empleado")

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    LazyColumn {
                        item {
                            Text(
                                "Datos personales",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF002B50)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = apellidos,
                                onValueChange = { apellidos = it },
                                label = { Text("Apellidos *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = dni,
                                onValueChange = { dni = it },
                                label = { Text("DNI *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = mail,
                                onValueChange = { mail = it },
                                label = { Text("Correo *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = movil,
                                onValueChange = { movil = it },
                                label = { Text("Teléfono *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = numeroSS,
                                onValueChange = { numeroSS = it },
                                label = { Text("NSS *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = fechaNacimiento,
                                onValueChange = { fechaNacimiento = it },
                                label = { Text("Fecha de nacimiento (dd/MM/yyyy) *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = fechaIngreso,
                                onValueChange = { fechaIngreso = it },
                                label = { Text("Fecha de ingreso (dd/MM/yyyy)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = tipoContrato,
                                onValueChange = { tipoContrato = it },
                                label = { Text("Tipo de contrato *") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = horasDiarias,
                                onValueChange = { horasDiarias = it },
                                label = { Text("Horas laborales diarias *") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Rol *", fontWeight = FontWeight.Bold)
                            Box {
                                OutlinedTextField(
                                    value = selectedRole?.name ?: "Seleccionar rol...",
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Rol") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.clickable { expandedRole = true }
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedRole,
                                    onDismissRequest = { expandedRole = false }
                                ) {
                                    roles.forEach { role ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedRole = role
                                                expandedRole = false
                                            },
                                            text = { Text(text = role.name) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Departamento *", fontWeight = FontWeight.Bold)
                            Box {
                                OutlinedTextField(
                                    value = selectedDepartment?.name ?: "Seleccionar departamento...",
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Departamento") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            modifier = Modifier.clickable { expandedDept = true }
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedDept,
                                    onDismissRequest = { expandedDept = false }
                                ) {
                                    departments.forEach { dept ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedDepartment = dept
                                                expandedDept = false
                                            },
                                            text = { Text(text = dept.name) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        val birthIso = DateUtils.toIsoFormat(fechaNacimiento) ?: ""
                                        val entryIso = DateUtils.toIsoFormat(fechaIngreso)

                                        val dto = UpdateEmployeeDto(
                                            id = selectedEmployee?.id,
                                            dni = dni,
                                            firstName = nombre,
                                            lastName = apellidos,
                                            email = mail,
                                            phone = movil,
                                            socialSecurityNumber = numeroSS,
                                            birthDate = birthIso,
                                            entryDate = entryIso,
                                            contractType = tipoContrato,
                                            dailyWorkingHours = horasDiarias.toIntOrNull() ?: 0,
                                            roleId = selectedRole!!.id!!,
                                            departmentId = selectedDepartment!!.id!!,
                                            supervisorId = null,
                                            address = selectedEmployee?.address,
                                            nationality = selectedEmployee?.nationality,
                                            gender = selectedEmployee?.gender,
                                            maritalStatus = selectedEmployee?.maritalStatus,
                                            numberOfChildren = selectedEmployee?.numberOfChildren,
                                            disability = selectedEmployee?.disability
                                        )
                                        staffViewModel.updateEmployee(employeeId, dto)
                                    },
                                    enabled = allRequiredFilled,
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00556E))
                                ) {
                                    Text("Guardar", color = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }
            }

            MenuGHotels(
                selectedIndex = 5,
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}