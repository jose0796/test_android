package com.example.domain.mapper

import com.example.data.model.*
import com.example.domain.model.*

fun Author.asPresentationModel() : PostAuthor =
    PostAuthor(
        id = this.id,
        name = this.name,
        email = this.email,
        address = AuthorAddress(
            street = this.address.street,
            suite = this.address.suite,
            city = this.address.city,
            zipcode = this.address.zipcode,
            geo = AuthorGeoLocation(
                this.address.geo.lat,
                this.address.geo.lng
            )
        ),
        company = AuthorCompany(
            name = this.company.name,
            bs =  this.company.bs,
            catchPhrase = this.company.catchPhrase
        ),
        phone = this.phone,
        website = this.website
    )

fun Comment.asPresentationModel() : PostComment =
    PostComment(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )



