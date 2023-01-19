package com.example.data.repository

import com.example.data.mappers.asBusinessModel
import com.example.data.mappers.asBusinessModelList
import com.example.data.mocks.TestPostDao
import com.example.data.mocks.TestRemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest {

    private lateinit var subject: PostRepositoryImpl

    private lateinit var postDao : TestPostDao

    private lateinit var remoteDataSource: TestRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = TestRemoteDataSource()
        postDao = TestPostDao()

        subject = PostRepositoryImpl(
            postDao = postDao,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun postRepository_post_data_stream_is_backed_by_posts_dao() =
        runTest {
            subject.loadPostsFromRemote()
            assertEquals(
                postDao.getPosts().first().asBusinessModelList(),
                subject.getPosts().first()
            )
        }

    @Test
    fun postRepository_loadingRemotePostsFromRepository_shouldMatchPostsFromDatabase() =
        runTest {
            subject.loadPostsFromRemote()
            assertEquals(
                postDao.getPosts().first().map { it.asBusinessModel() },
                subject.getPosts().first()
            )
        }

    @Test
    fun postRepository_gettingPostsFromRemote_shouldMatchPostsFromRepository() =
        runTest {
            assertEquals(
                remoteDataSource.getPosts().map{ it.asBusinessModel() },
                subject.getPosts().first()
            )
        }

    @Test
    fun postRepository_deletingPostFromDatabase_gettingPostsFromDao_shouldMatchPostsFromRepository() =
        runTest {
            subject.loadPostsFromRemote() // load and store all posts from API

            val randomPostId = postDao.getPosts().first().map { it.id }.random()
            postDao.deletePost(randomPostId)

            assertEquals(
                postDao.getPosts().first().map { it.asBusinessModel() },
                subject.getPosts().first()
            )
        }

    @Test
    fun postRepository_deletingPostsFromDatabase_andGettingPostsFromDatabase_shouldMatchPostsFromRepository() =
        runTest {
            subject.loadPostsFromRemote() // load and store all posts from API

            val idSet = postDao.getPosts().first().map { it.id }.toSet()
            val ids = (0..idSet.size / 2).map { idSet.random() }.toSet().toList()
            postDao.deletePosts(ids)

            assertEquals(
                postDao.getPosts().first().map { it.asBusinessModel() },
                subject.getPosts().first()
            )
        }

    @Test
    fun postRepository_gettingPostDetailsFromRepository_shouldMatchPostDetailFromDao() =
        runTest {
            subject.loadPostsFromRemote() // load and store all posts from API

            val randomPostId = postDao.getPosts().first().map { it.id }.random()
            val postDetailFromRepository = subject.getPostDetail(randomPostId).first()
            val postDetailFromDB = postDao.getPostsDetails(randomPostId).first().asBusinessModel()


            assertEquals(
                postDetailFromDB.post,
                postDetailFromRepository.post
            )

            assertEquals(
                postDetailFromDB.comments,
                postDetailFromRepository.comments
            )

            assertEquals(
                postDetailFromDB.author,
                postDetailFromRepository.author
            )
        }
}