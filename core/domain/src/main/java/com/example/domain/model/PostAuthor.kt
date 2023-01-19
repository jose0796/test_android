package com.example.domain.model

data class PostAuthor(
    val id: Int,
    val name: String,
    val email: String,
    val address: AuthorAddress,
    val phone: String,
    val website: String,
    val company: AuthorCompany
) {
    val completeAddress: String
        get() {
            var str = ""
            if (address.suite.isNotEmpty()) {
                str = address.suite
            }

            if (address.street.isNotEmpty()) {
                str = str + ", " + address.street
            }

            if (address.city.isNotEmpty()) {
                str = str + ", " + address.city
            }

            if (address.zipcode.isNotEmpty()) {
                str = str + ", " + address.zipcode
            }

            return str
        }
}

data class AuthorCompany(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class AuthorAddress(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: AuthorGeoLocation
)

data class AuthorGeoLocation(
    val lat: Double,
    val lng: Double
)