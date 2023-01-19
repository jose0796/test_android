package com.example.data.mocks

import com.example.core.network.RemoteDataSource
import com.example.core.network.fake.FakeRemoteApi
import com.example.core.network.model.AuthorDto
import com.example.core.network.model.CommentDto
import com.example.core.network.model.PostDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestRemoteDataSource : RemoteDataSource {

    private val api = FakeRemoteApi(UnconfinedTestDispatcher())

    private val allPosts = runBlocking { api.getPosts() }

    override suspend fun getPosts(): List<PostDto> = allPosts

    override suspend fun getPostAuthor(id: Int): AuthorDto = api.getPostAuthor(id)

    override suspend fun getPostComments(id: Int): List<CommentDto> = api.getPostComments(id)
}