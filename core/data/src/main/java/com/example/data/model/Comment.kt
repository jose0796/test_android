package com.example.data.model

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) {
    companion object {
        fun mock(id: Int, postId: Int) = Comment(postId, id, "","", "")
    }
}