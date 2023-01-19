package com.example.core.database.model

import androidx.room.Entity

@Entity(
    tableName = "comments",
    primaryKeys = ["postId", "id"]
)
data class CommentEntity(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)