package com.example.core.network.model

data class AuthorDto(
    val id: Int,
    val name: String,
    val email: String,
    val address: AddressDto,
    val phone: String,
    val website: String,
    val company: CompanyDto
)

data class CompanyDto(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class AddressDto(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoLocationDto
)

data class GeoLocationDto(
    val lat: Double,
    val lng: Double
)

