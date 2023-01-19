package com.example.domain.model

data class UserPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean
)