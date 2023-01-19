package com.example.data.model

data class Author(
    val id: Int,
    val name: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) {
    companion object {
        fun mock(id: Int) =
            Author(
                id = id,
                name = "",
                email = "",
                address = Address.mock(),
                phone = "",
                website = "",
                company = Company.mock()
            )
    }
}

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
) {
    companion object {
        fun mock() =
            Company("","","")
    }
}

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoLocation
) {
    companion object {
        fun mock() =
            Address(
                street = "",
                suite = "",
                city = "",
                zipcode = "",
                geo = GeoLocation.mock()
            )
    }
}

data class GeoLocation(
    val lat: Double,
    val lng: Double
) {
    companion object {
        fun mock() = GeoLocation(0.0,0.0)
    }
}