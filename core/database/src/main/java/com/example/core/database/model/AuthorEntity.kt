package com.example.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "authors"
)
data class AuthorEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    @Embedded
    val address: AddressEntity,
    val phone: String,
    val website: String,
    @Embedded
    val company: CompanyEntity
)

data class CompanyEntity(
    val companyName: String,
    val catchPhrase: String,
    val bs: String
)

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo: GeoLocationEntity
)

data class GeoLocationEntity(
    val lat: Double,
    val lng: Double
)