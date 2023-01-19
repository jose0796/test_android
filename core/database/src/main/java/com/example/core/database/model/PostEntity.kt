package com.example.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
)
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    @ColumnInfo(defaultValue = "")
    val title: String,
    @ColumnInfo(defaultValue = "")
    val body: String
)
