package com.example.core.database.dao

import androidx.room.*
import com.example.core.database.model.AuthorEntity
import com.example.core.database.model.CommentEntity
import com.example.core.database.model.PostDetailEntity
import com.example.core.database.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getPosts() : Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts WHERE id == :id")
    fun deletePost(id: Int)

    @Transaction
    @Query("DELETE FROM posts WHERE id in (:ids)")
    fun deletePosts(ids: List<Int>)

    @Transaction
    @Query("SELECT * FROM posts WHERE id == :id")
    fun getPostsDetails(id: Int): Flow<PostDetailEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAuthor(author: AuthorEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreComments(comments: List<CommentEntity>)


}