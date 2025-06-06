package com.example.ghotels.presentation.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
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

    // — Campos a editar —
    var nombre by rememberSaveable   { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var dni by rememberSaveable { mutableStateOf("") }
    var mail by rememberSaveable { mutableStateOf("") }
    var movil by rememberSaveable { mutableStateOf("") }
    var numeroSS by rememberSaveable { mutableStateOf("") }
    var fechaNacimiento by rememberSaveable { mutableStateOf("") }
    var fechaIngreso by rememberSaveable { mutableStateOf("") }
    var tipoContrato by rememberSaveable { mutableStateOf("") }
    var horasDiarias by rememberSaveable { mutableStateOf("") }

    // — Rol/Departamento (solo en memoria) —
    var selectedRole by remember { mutableStateOf<Role?>(null) }
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var expandedRole by rememberSaveable { mutableStateOf(false) }
    var expandedDept by rememberSaveable { mutableStateOf(false) }

    // — Dirección (no obligatorio) —
    var street by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var postalCode by rememberSaveable { mutableStateOf("") }
    var province by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }

    // — Datos adicionales (no obligatorios) —
    var nationality by rememberSaveable { mutableStateOf("") }

    var gender by rememberSaveable { mutableStateOf<String?>(null) }
    var expandedGender by rememberSaveable { mutableStateOf(false) }
    val genderOptions = listOf("Masculino", "Femenino", "Otro")

    var maritalStatus by rememberSaveable { mutableStateOf<String?>(null) }
    var expandedMarital by rememberSaveable { mutableStateOf(false) }
    val maritalOptions = listOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a")

    var numberOfChildren by rememberSaveable { mutableStateOf("") }
    var disability by rememberSaveable { mutableStateOf(false) }

    // — Cargar datos del empleado al entrar —
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

            // Dirección
            emp.address?.let { addr ->
                street = addr.street
                postalCode = addr.postalCode
                city = addr.city
                province = addr.province
                country = addr.country
            }

            // Datos adicionales
            nationality = emp.nationality ?: ""
            gender = emp.gender
            maritalStatus = emp.maritalStatus
            numberOfChildren = emp.numberOfChildren?.toString() ?: ""
            disability = emp.disability ?: false
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

    // Si aún no llegó el empleado, mostrar loader
    if (selectedEmployee == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    // Cuando se guarde exitosamente, volver atrás una sola vez
    LaunchedEffect(updateSuccess) {
        if (updateSuccess == true) {
            navController.popBackStack()
        }
    }

    //  Validaciones en memoria
    val dniRegex = remember { Regex("^[0-9]{8}[A-Za-z]\$") }
    val ssnRegex = remember { Regex("^[0-9]{12}\$") }
    val phoneRegex = remember { Regex("^[0-9]{9}\$") }
    val emailPattern = remember { android.util.Patterns.EMAIL_ADDRESS }
    val postalCodeRegex = remember { Regex("^[0-9]{5}\$") }

    // Errores individuales
    val dniError = dni.isNotBlank() && !dniRegex.matches(dni)
    val ssnError = numeroSS.isNotBlank() && !ssnRegex.matches(numeroSS)
    val phoneError = movil.isNotBlank() && !phoneRegex.matches(movil)
    val emailError = mail.isNotBlank() && !emailPattern.matcher(mail).matches()
    val horasInt = horasDiarias.toIntOrNull()
    val horasError = horasDiarias.isNotBlank() && (horasInt == null || horasInt !in 1..9)

    val birthDateIso = DateUtils.toIsoFormat(fechaNacimiento)
    val birthDateError = fechaNacimiento.isNotBlank() && birthDateIso == null
    val entryDateIso = DateUtils.toIsoFormat(fechaIngreso)
    val entryDateError = fechaIngreso.isNotBlank() && entryDateIso == null

    val postalCodeError = postalCode.isNotBlank() && !postalCodeRegex.matches(postalCode)
    val childrenError = numberOfChildren.isNotBlank() && numberOfChildren.toIntOrNull() == null

    // Solo estos campos son obligatorios
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

    // Para habilitar “Guardar”
    val allValid = allRequiredFilled &&
            !dniError &&
            !emailError &&
            !phoneError &&
            !ssnError &&
            !birthDateError &&
            !entryDateError &&
            !horasError

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF002B50)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopGHotels(title = "Editar Empleado")
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Datos personales (obligatorios)
                        item {
                            Text(
                                "Datos personales",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF002B50)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Nombre
                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Apellidos
                            OutlinedTextField(
                                value = apellidos,
                                onValueChange = { apellidos = it },
                                label = { Text("Apellidos *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // DNI
                            OutlinedTextField(
                                value = dni,
                                onValueChange = { dni = it },
                                label = { Text("DNI *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = dniError,
                                singleLine = true
                            )
                            if (dniError) {
                                Text(
                                    text = "El DNI debe tener 8 dígitos y una letra (sin espacios).",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Correo
                            OutlinedTextField(
                                value = mail,
                                onValueChange = { mail = it },
                                label = { Text("Correo *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = emailError,
                                singleLine = true
                            )
                            if (emailError) {
                                Text(
                                    text = "Introduce un correo válido.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Teléfono
                            OutlinedTextField(
                                value = movil,
                                onValueChange = { movil = it },
                                label = { Text("Teléfono *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = phoneError,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            if (phoneError) {
                                Text(
                                    text = "El teléfono debe tener 9 dígitos numéricos.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // NSS
                            OutlinedTextField(
                                value = numeroSS,
                                onValueChange = { numeroSS = it },
                                label = { Text("NSS *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = ssnError,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            if (ssnError) {
                                Text(
                                    text = "El NSS debe tener exactamente 12 dígitos numéricos.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Fecha de nacimiento
                            OutlinedTextField(
                                value = fechaNacimiento,
                                onValueChange = { fechaNacimiento = it },
                                label = { Text("Fecha de nacimiento (dd/MM/yyyy) *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = birthDateError,
                                singleLine = true
                            )
                            if (birthDateError) {
                                Text(
                                    text = "Formato de fecha inválido. Usa dd/MM/yyyy.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Fecha de ingreso
                            OutlinedTextField(
                                value = fechaIngreso,
                                onValueChange = { fechaIngreso = it },
                                label = { Text("Fecha de ingreso (dd/MM/yyyy) *") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = entryDateError,
                                singleLine = true
                            )
                            if (entryDateError) {
                                Text(
                                    text = "Formato de fecha inválido. Usa dd/MM/yyyy.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Tipo de contrato
                            OutlinedTextField(
                                value = tipoContrato,
                                onValueChange = { tipoContrato = it },
                                label = { Text("Tipo de contrato *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Horas laborales diarias (1–9)
                            OutlinedTextField(
                                value = horasDiarias,
                                onValueChange = { horasDiarias = it },
                                label = { Text("Horas laborales diarias (1–9) *") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                isError = horasError,
                                singleLine = true
                            )
                            if (horasError) {
                                Text(
                                    text = "Introduce un número entre 1 y 9.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // — Sección: Dirección (no obligatorio) —
                        item {
                            Text(
                                "Dirección",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF002B50)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = street,
                                onValueChange = { street = it },
                                label = { Text("Calle y número") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = postalCode,
                                onValueChange = { postalCode = it },
                                label = { Text("Código postal") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                isError = postalCodeError
                            )
                            if (postalCodeError) {
                                Text(
                                    text = "El código postal debe tener exactamente 5 dígitos numéricos.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = city,
                                onValueChange = { city = it },
                                label = { Text("Ciudad") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = province,
                                onValueChange = { province = it },
                                label = { Text("Provincia") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = country,
                                onValueChange = { country = it },
                                label = { Text("País") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // — Sección: Datos adicionales (no obligatorios) —
                        item {
                            Text(
                                "Datos adicionales",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF002B50)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Nacionalidad
                            OutlinedTextField(
                                value = nationality,
                                onValueChange = { nationality = it },
                                label = { Text("Nacionalidad") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Género (dropdown)
                            Text("Género")
                            Box {
                                OutlinedTextField(
                                    value = gender ?: "Seleccionar género...",
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            Modifier.clickable { expandedGender = true }
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedGender,
                                    onDismissRequest = { expandedGender = false }
                                ) {
                                    genderOptions.forEach { g ->
                                        DropdownMenuItem(
                                            text = { Text(g) },
                                            onClick = {
                                                gender = g
                                                expandedGender = false
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Estado civil (dropdown)
                            Text("Estado civil")
                            Box {
                                OutlinedTextField(
                                    value = maritalStatus ?: "Seleccionar estado civil...",
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            Modifier.clickable { expandedMarital = true }
                                        )
                                    }
                                )
                                DropdownMenu(
                                    expanded = expandedMarital,
                                    onDismissRequest = { expandedMarital = false }
                                ) {
                                    maritalOptions.forEach { m ->
                                        DropdownMenuItem(
                                            text = { Text(m) },
                                            onClick = {
                                                maritalStatus = m
                                                expandedMarital = false
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Número de hijos (solo números)
                            OutlinedTextField(
                                value = numberOfChildren,
                                onValueChange = { numberOfChildren = it },
                                label = { Text("Número de hijos") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                isError = childrenError
                            )
                            if (childrenError) {
                                Text(
                                    text = "Introduce un número válido.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Discapacidad (checkbox)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = disability,
                                    onCheckedChange = { disability = it }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Discapacidad")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // — Sección: Rol y Departamento (obligatorios) —
                        item {
                            Text("Rol *", fontWeight = FontWeight.Bold)
                            Box {
                                OutlinedTextField(
                                    value = selectedRole?.name ?: "Seleccionar rol...",
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Rol") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            Modifier.clickable { expandedRole = true }
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
                                            text = { Text(role.name) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Departamento *", fontWeight = FontWeight.Bold)
                            Box {
                                OutlinedTextField(
                                    value = selectedDepartment?.name
                                        ?: "Seleccionar departamento...",
                                    onValueChange = { },
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Departamento") },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            Modifier.clickable { expandedDept = true }
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
                                            text = { Text(dept.name) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        // — Botones: Volver + Guardar —
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { navController.popBackStack() },
                                    border = BorderStroke(1.dp, Color.Gray),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                ) {
                                    Text("Volver")
                                }

                                Button(
                                    onClick = {
                                        // Ya sabemos que selectedEmployee no es nulo en este momento
                                        val emp = selectedEmployee!!

                                        // Convertir fechas (pueden ser nulas si el usuario borró el campo, pero validamos antes)
                                        val birthIso = DateUtils.toIsoFormat(fechaNacimiento) ?: fechaNacimiento
                                        val entryIso = DateUtils.toIsoFormat(fechaIngreso)

                                        // Construir el AddressDto usando los campos de estado
                                        val newAddress = AddressDto(
                                            street = street.ifBlank { emp.address?.street.orEmpty() },
                                            postalCode = postalCode.ifBlank { emp.address?.postalCode.orEmpty() },
                                            city = city.ifBlank { emp.address?.city.orEmpty() },
                                            province = province.ifBlank { emp.address?.province.orEmpty() },
                                            country = country.ifBlank { emp.address?.country.orEmpty() }
                                        )

                                        // Construir el DTO con TODOS los campos (obligatorios y opcionales)
                                        val dto = UpdateEmployeeDto(
                                            id = emp.id,
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
                                            supervisorId = null, // o emp.supervisorId si lo tuvieras
                                            address = newAddress,
                                            nationality = nationality.ifBlank { emp.nationality.orEmpty() }.ifBlank { null },
                                            gender = gender ?: emp.gender,
                                            maritalStatus = maritalStatus ?: emp.maritalStatus,
                                            numberOfChildren = numberOfChildren.toIntOrNull() ?: emp.numberOfChildren ?: 0,
                                            disability = disability
                                        )
                                        staffViewModel.updateEmployee(employeeId, dto)
                                    },
                                    enabled = allValid,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
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