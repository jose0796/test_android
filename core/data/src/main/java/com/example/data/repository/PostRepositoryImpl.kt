package com.example.data.repository

import com.example.core.database.dao.PostDao
import com.example.core.network.RemoteDataSource
import com.example.core.network.model.PostDto
import com.example.data.mappers.asBusinessModel
import com.example.data.mappers.asBusinessModelList
import com.example.data.mappers.asEntityModel
import com.example.data.model.Post
import com.example.data.model.PostDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postDao : PostDao,
    private val remoteDataSource: RemoteDataSource
) : PostRepository {

    override fun getPosts() : Flow<List<Post>> =
        postDao.getPosts().map { localPosts ->
            if (localPosts.isNotEmpty()) {
                return@map localPosts.asBusinessModelList()
            }

            val remotePosts = remoteDataSource.getPosts()
            postDao.insertOrIgnorePosts(remotePosts.map(PostDto::asEntityModel))

            return@map remotePosts.map(PostDto::asBusinessModel)
        }.flowOn(Dispatchers.IO)

    override suspend fun loadPostsFromRemote() : Boolean =
        try {
            val newPosts = remoteDataSource.getPosts().map(PostDto::asEntityModel)
            postDao.insertOrIgnorePosts(newPosts)
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }


    override suspend fun deletePost(id: Int) : Boolean =
        try {
            postDao.deletePost(id)
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }

    override suspend fun deletePosts(ids: List<Int>) : Boolean =
        try {
            postDao.deletePosts(ids)
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }


    override fun getPostDetail(id: Int): Flow<PostDetail> =
        postDao.getPostsDetails(id).map { postDetail ->
            if (postDetail.author == null ) {
                val author = remoteDataSource.getPostAuthor(postDetail.post.userId).asEntityModel()
                postDao.insertOrIgnoreAuthor(author)
                postDetail.author = author
            }

            if (postDetail.comments.isEmpty()) {
                val comments = remoteDataSource.getPostComments(postDetail.post.id).map { it.asEntityModel() }
                postDao.insertOrIgnoreComments(comments)
                postDetail.comments = comments
            }

            return@map postDetail.asBusinessModel()

        }


}