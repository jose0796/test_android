package com.example.core.network.fake

import JvmUnitTestFakeAssetManager
import android.content.res.Resources.NotFoundException
import com.example.core.network.model.AuthorDto
import com.example.core.network.model.CommentDto
import com.example.core.network.model.PostDto
import com.example.core.network.retrofit.RemoteApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeRemoteApi
 @Inject constructor(
    val ioDispatcher: CoroutineDispatcher,
    val assets: FakeAssetManager = JvmUnitTestFakeAssetManager
): RemoteApi {

    companion object {
        private const val AUTHORS_ASSET = "authors.json"
        private const val POST_ASSET = "posts.json"
        private const val COMMENTS_ASSET = "comments.json"
    }

    override suspend fun getPosts(): List<PostDto> =
        withContext(ioDispatcher) {
            MoshiParser.fromJson(getListType<PostDto>(), assets.open(POST_ASSET))?.toList() ?: listOf()
        }

    override suspend fun getPostAuthor(id: Int): AuthorDto =
        withContext(ioDispatcher) {
            MoshiParser.fromJson(
                getListType<AuthorDto>(), assets.open(AUTHORS_ASSET)
            )?.let {
                return@withContext  it.first { it.id == id }
            }

            throw NotFoundException( "Author not found" )
        }

    override suspend fun getPostComments(id: Int): List<CommentDto> =
        withContext(ioDispatcher) {
            (MoshiParser.fromJson(
                getListType<CommentDto>(), assets.open(COMMENTS_ASSET)
            )?.toList() ?: listOf()).filter{ it.postId == id }
        }

}