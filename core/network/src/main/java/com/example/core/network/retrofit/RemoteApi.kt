package com.example.core.network.retrofit

import com.example.core.network.model.AuthorDto
import com.example.core.network.model.CommentDto
import com.example.core.network.model.PostDto
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface RemoteApi {

    @GET(value = "posts")
    suspend fun getPosts(): List<PostDto>

    @GET(value = "users/{id}")
    suspend fun getPostAuthor(@Path("id") id: Int): AuthorDto

    @GET(value = "posts/{id}/comments")
    suspend fun getPostComments(@Path("id") id: Int): List<CommentDto>

}