package com.example.core.network

import com.example.core.network.model.AuthorDto
import com.example.core.network.model.CommentDto
import com.example.core.network.model.PostDto

interface RemoteDataSource {

    suspend fun getPosts() : List<PostDto>

    suspend fun getPostAuthor(id: Int) : AuthorDto

    suspend fun getPostComments(id: Int ) : List<CommentDto>

}