package com.example.data.repository

import com.example.data.model.Post
import com.example.data.model.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<List<Post>>

    suspend fun loadPostsFromRemote() : Boolean

    suspend fun deletePost(id: Int) : Boolean

    suspend fun deletePosts(ids: List<Int>) : Boolean

    fun getPostDetail(id: Int) : Flow<PostDetail>


}