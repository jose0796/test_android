package com.example.data.model

class PostDetail(
    val post: Post,
    val author: Author?,
    val comments: List<Comment>
)