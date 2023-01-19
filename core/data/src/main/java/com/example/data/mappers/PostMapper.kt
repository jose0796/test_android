package com.example.data.mappers

import com.example.core.database.model.*
import com.example.core.network.model.*
import com.example.data.model.*

fun PostEntity.asBusinessModel() : Post =
    Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )

fun Collection<PostEntity>.asBusinessModelList(): List<Post> =
    this.map(PostEntity::asBusinessModel)

fun PostDto.asBusinessModel() : Post =
    Post(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )

fun PostDto.asEntityModel() : PostEntity =
    PostEntity(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )

fun AuthorEntity.asBusinessModel() : Author =
    Author(
        id = this.id,
        name = this.name,
        email = this.email,
        address = Address(
            street = this.address.street,
            suite = this.address.suite,
            city = this.address.city,
            zipcode = this.address.zipcode,
            geo = GeoLocation(
                this.address.geo.lat,
                this.address.geo.lng
            )
        ),
        company = Company(
            name = this.company.companyName,
            bs =  this.company.bs,
            catchPhrase = this.company.catchPhrase
        ),
        phone = this.phone,
        website = this.website
    )

fun CommentEntity.asBusinessModel() : Comment =
    Comment(
        postId = this.postId,
        id = this.id,
        name = this.name,
        email = this.email,
        body = this.body
    )

fun AuthorDto.asEntityModel() : AuthorEntity =
    AuthorEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        address = AddressEntity(
            street = this.address.street,
            suite = this.address.suite,
            city = this.address.city,
            zipcode = this.address.zipcode,
            geo = GeoLocationEntity(
                this.address.geo.lat,
                this.address.geo.lng
            )
        ),
        company = CompanyEntity(
            companyName = this.company.name,
            bs =  this.company.bs,
            catchPhrase = this.company.catchPhrase
        ),
        phone = this.phone,
        website = this.website
    )

fun CommentDto.asEntityModel() : CommentEntity =
    CommentEntity(
        postId = this.postId,
        id = this.id,
        body = this.body,
        email = this.email,
        name = this.name
    )

fun PostDetailEntity.asBusinessModel() : PostDetail =
    PostDetail(
        post = this.post.asBusinessModel(),
        author = this.author?.asBusinessModel(),
        comments = this.comments.map { it.asBusinessModel() }
    )
