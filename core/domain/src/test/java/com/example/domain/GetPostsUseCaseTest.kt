package com.example.domain

import com.example.data.fake.TestPostsRepository
import com.example.data.fake.TestUserRepository
import com.example.data.model.Post
import com.example.domain.model.UserPost
import com.example.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostsUseCaseTest {

    private val testPosts = listOf(
        Post(userId = 1, id = 1, title = "", body = ""),
        Post(userId = 2, id = 2, title = "", body = ""),
        Post(userId = 3, id = 3 , title = "", body = ""),
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val postRepository = TestPostsRepository()
    private val userRepository = TestUserRepository()

    val useCase = GetPostsUseCase(
        postRepository,
        userRepository
    )


    @Test
    fun getting_user_posts_test() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(testPosts.first().id, true)
        val posts = useCase()

        // Check that the followable topics are sorted by the topic name.
        assertEquals(
            listOf(
                UserPost(userId = 1, id = 1, title = "", body = "", true ),
                UserPost(userId = 2, id = 2, title = "", body = "", false ),
                UserPost(userId = 3, id = 3, title = "", body = "", false),
            ),
            posts.first()
        )
    }
}