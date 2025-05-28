package com.example.ghotels.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ghotels.domain.usecase.employee.SaveUserUseCase
import com.example.ghotels.domain.usecase.employee.UpdateProfileUseCase
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ghotels.data.model.EmergencyContactDto
import com.example.ghotels.data.model.AddressDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    // Guardo en memoria persistente (DataStore)
    val user = saveUserUseCase.user

    // Guardado exitoso???
    private val _saveStatus = MutableStateFlow<Boolean?>(null)
    val saveStatus: StateFlow<Boolean?> = _saveStatus


    fun updateField(field: String, value: String) {
        val current = user.value ?: return

        val currentAddress = current.address ?: AddressDto("", "", "", "", "")

        val updatedAddress = when (field) {
            "addressStreet" -> currentAddress.copy(street = value)
            "addressCity" -> currentAddress.copy(city = value)
            "addressProvince" -> currentAddress.copy(province = value)
            "addressPostalCode" -> currentAddress.copy(postalCode = value)
            "addressCountry" -> currentAddress.copy(country = value)
            else -> currentAddress
        }

        val updated = when (field) {
            "email" -> current.copy(email = value)
            "phone" -> current.copy(phone = value)
            "maritalStatus" -> current.copy(maritalStatus = value)
            "nationality" -> current.copy(nationality = value)
            "gender" -> current.copy(gender = value)
            "addressStreet", "addressCity", "addressProvince", "addressPostalCode", "addressCountry" ->
                current.copy(address = updatedAddress)
            else -> current
        }

        // Guardamos el usuario actualizado en memoria persistente
        viewModelScope.launch {
            saveUserUseCase.setUser(updated)
        }
    }


    fun updateEmergencyContact(name: String, phone: String, relation: String) {
        val current = user.value ?: return
        val existingContact = current.emergencyContact ?: EmergencyContactDto(
            id = null,
            name = "",
            phone = "",
            relationship = "",
            employeeId = current.id
        )

        val updatedContact = existingContact.copy(
            name = name,
            phone = phone,
            relationship = relation
        )

        val updatedUser = current.copy(emergencyContact = updatedContact)

        viewModelScope.launch {
            saveUserUseCase.setUser(updatedUser)
        }
    }

    /**
     * Guarda todos los cambios del perfil en el backend
     */
    fun saveChanges() {
        val current = user.value ?: return

        viewModelScope.launch {
            val result = updateProfileUseCase(current.id ?: return@launch, current)
            _saveStatus.value = result.isSuccess
        }
    }


    fun logout(navController: NavController) {
        viewModelScope.launch {
            saveUserUseCase.clearUser() // Limpia de memoria y DataStore
            navController.navigate("login") {
                popUpTo("home") { inclusive = true } // Elimina Home del backstack
            }
        }
    }

}