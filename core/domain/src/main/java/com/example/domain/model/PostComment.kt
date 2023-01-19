package com.example.domain.model

data class PostComment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)