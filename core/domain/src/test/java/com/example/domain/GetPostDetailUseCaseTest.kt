package com.example.domain

import com.example.data.fake.TestPostsRepository
import com.example.data.fake.TestUserRepository
import com.example.data.model.Author
import com.example.data.model.Comment
import com.example.data.model.Post
import com.example.domain.model.UserPost
import com.example.domain.model.UserPostDetail
import com.example.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostDetailUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val postRepository = TestPostsRepository()
    private val userRepository = TestUserRepository()

    val useCase = GetPostDetailUseCase(
        postRepository,
        userRepository
    )


    @Test
    fun getting_user_posts_details_test() = runTest {

        postRepository.sendPosts(testPosts)
        postRepository.sendAuthors(testAuthor)
        postRepository.sendComments(testComments)
        val selectedPostId = testPosts.first().id
        userRepository.toggleFavoritePostId(selectedPostId, true)
        val postsDetails = useCase(selectedPostId)

        Assert.assertEquals(
            expectedUserPostDetail.first(),
            postsDetails.first()
        )
    }



    private val testPosts = listOf(
        Post(userId = 1, id = 1, title = "", body = ""),
        Post(userId = 2, id = 2, title = "", body = ""),
        Post(userId = 3, id = 3 , title = "", body = ""),
    )

    private val testAuthor = listOf(
        Author.mock(1),
        Author.mock(2),
        Author.mock(3),
    )

    private val testComments = listOf(
        Comment.mock(1, 1),
        Comment.mock(2,2),
        Comment.mock(3,3),
    )

    private val expectedUserPostDetail = listOf(
        UserPostDetail.mock(userId = 1, postId = 1, favorite = true),
        UserPostDetail.mock(userId = 2, postId = 2, favorite = false),
        UserPostDetail.mock(userId = 3, postId = 3, favorite = false),
    )
}