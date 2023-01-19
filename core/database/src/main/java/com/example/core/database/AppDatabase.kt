package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.PostDao
import com.example.core.database.model.AuthorEntity
import com.example.core.database.model.CommentEntity
import com.example.core.database.model.PostEntity

@Database(
    entities = [
        PostEntity::class,
        CommentEntity::class,
        AuthorEntity::class
    ],
    version = 2,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
