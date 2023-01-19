package com.example.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class PostDetailEntity (
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    var comments: List<CommentEntity>,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    var author: AuthorEntity?
)