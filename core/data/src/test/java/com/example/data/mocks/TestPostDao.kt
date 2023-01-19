package com.example.data.mocks

import com.example.core.database.dao.PostDao
import com.example.core.database.model.*
import kotlinx.coroutines.flow.*

class TestPostDao : PostDao{

    private var postStateFlow = MutableStateFlow(
        listOf<PostEntity>()
    )

    private var commentsStateFlow = MutableStateFlow(
        listOf<CommentEntity>()
    )

    private var authorsStateFlow = MutableStateFlow(
        listOf<AuthorEntity>()
    )

    override fun getPosts(): Flow<List<PostEntity>> = postStateFlow

    override suspend fun insertOrIgnorePosts(posts: List<PostEntity>) {
        postStateFlow.value = posts
    }

    override fun deletePost(id: Int) {
        postStateFlow.update { posts ->
            posts.filterNot { id == it.id }
        }
    }

    override fun deletePosts(ids: List<Int>) {
        val set = ids.toSet()
        postStateFlow.update { posts ->
            posts.filterNot { set.contains(it.id) }
        }
    }

    override fun getPostsDetails(id: Int): Flow<PostDetailEntity> =
        combine(
            postStateFlow,
            authorsStateFlow,
            commentsStateFlow
        ) { posts, authors, comments ->

            val post = posts.first { it.id == id }
            val author = authors.firstOrNull { it.id == post.userId }
            val comments = comments.filter { it.postId == post.id }

           PostDetailEntity(
               post = post,
               author = author,
               comments = comments
           )
        }

    override suspend fun insertOrIgnoreAuthor(author: AuthorEntity) {
        val authors = authorsStateFlow.first().toMutableList()
        if (authors.any { it.id == author.id }) return
        authors.add(author)
        authorsStateFlow.value = authors
    }

    override suspend fun insertOrIgnoreComments(comments: List<CommentEntity>) {
        commentsStateFlow.value = comments
    }

}