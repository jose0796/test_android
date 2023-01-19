package com.example.data.fake

import com.example.data.model.Author
import com.example.data.model.Comment
import com.example.data.model.Post
import com.example.data.model.PostDetail
import com.example.data.repository.PostRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class TestPostsRepository : PostRepository {

    private val postsFlow: MutableSharedFlow<List<Post>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val authorsFlow: MutableSharedFlow<List<Author>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val commentsFlow: MutableSharedFlow<List<Comment>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun sendPosts(posts: List<Post>) {
        postsFlow.tryEmit(posts)
    }

    fun sendAuthors(authors: List<Author>) {
        authorsFlow.tryEmit(authors)
    }

    fun sendComments(comments: List<Comment>) {
        commentsFlow.tryEmit(comments)
    }

    override fun getPosts(): Flow<List<Post>> = postsFlow

    override suspend fun loadPostsFromRemote(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(id: Int): Boolean {
        try  {
            val list = postsFlow.first().toMutableSet()

            if (!list.any { it.id == id }) {
                return false
            }

            list.removeIf { it.id == id }
            postsFlow.tryEmit(list.toList())
            return true
        }catch (e: Exception){
            return false
        }
    }

    override suspend fun deletePosts(ids: List<Int>): Boolean {
        try  {
            val list = postsFlow.first().toMutableSet()

            if (!list.any { it.id in ids }) {
                return false
            }

            list.removeIf { it.id in ids }
            postsFlow.tryEmit(list.toList())
            return true
        }catch (e: Exception){
            return false
        }
    }

    override fun getPostDetail(id: Int): Flow<PostDetail> =
        combine(
            postsFlow,
            authorsFlow,
            commentsFlow
        ) { posts, authors, comments ->
            val post = posts.first { it.id == id }
            val author = authors.firstOrNull { it.id == post.userId}
            val comments = comments.filter { it.postId == post.id }

            PostDetail(
                post = post,
                author = author,
                comments = comments
            )
        }
}