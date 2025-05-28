package com.example.ghotels.data.model

import com.example.ghotels.domain.model.Address

data class AddressDto(
    val street: String,
    val postalCode: String,
    val city: String,
    val province: String,
    val country: String
) {
    fun toAddress(): Address = Address(street, postalCode, city, province, country)

    companion object {
        fun from(address: Address) = AddressDto(
            street = address.street,
            postalCode = address.postalCode,
            city = address.city,
            province = address.province,
            country = address.country
        )
    }
}
