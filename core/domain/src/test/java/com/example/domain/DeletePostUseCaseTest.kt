package com.example.domain

import com.example.data.fake.TestPostsRepository
import com.example.data.fake.TestUserRepository
import com.example.data.model.Author
import com.example.data.model.Comment
import com.example.data.model.Post
import com.example.domain.model.UserPostDetail
import com.example.testing.util.MainDispatcherRule
import com.google.common.truth.ExpectFailure.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeletePostUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val postRepository = TestPostsRepository()
    private val userRepository = TestUserRepository()

    val useCase = DeletePostUseCase(
        postRepository,
        userRepository
    )

    @Test
    fun deleteNotFavoritePost_shouldReturnTrue() = runTest {

        postRepository.sendPosts(testPosts)
        val selectedPostId = testPosts.first().id
        val favoriteSelectedPostId = testPosts[1].id
        userRepository.toggleFavoritePostId(favoriteSelectedPostId, true)
        val wasDeleted = useCase(selectedPostId)

        testPosts.removeAt(0)

        assertEquals(wasDeleted, true)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        // when removing non favorite posts favoritePostIds should remain intact
        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(favoriteSelectedPostId)
        )
    }

    @Test
    fun deleteMultipleNotFavoritePost_shouldReturnTrue() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(3, true)

        val selectedPostIds = listOf(1,2)
        val wasDeleted = useCase(selectedPostIds)

        testPosts.removeFirst()
        testPosts.removeFirst()

        assertEquals(wasDeleted, true)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(3)
        )
    }

    @Test
    fun deleteFavoritePost_shouldRemoveFavoriteId_shouldReturnTrue() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(1, true)

        val selectedPostId = 1
        val wasDeleted = useCase(selectedPostId)

        testPosts.removeIf { it.id == 1 }

        assertEquals(wasDeleted, true)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            emptySet<Int>()
        )
    }

    @Test
    fun deleteMultipleFavoritePost_shouldRemoveFavoriteId_shouldReturnTrue() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(1, true)
        userRepository.toggleFavoritePostId(2, true)

        val selectedPostIds = listOf(1,2)
        val wasDeleted = useCase(selectedPostIds)

        testPosts.removeIf { it.id == 1 }
        testPosts.removeIf { it.id == 2 }

        assertEquals(wasDeleted, true)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            emptySet<Int>()
        )
    }

    @Test
    fun deleteUnknownPostId_shouldReturnFalse() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(1, true)

        val unknownPostId = 4
        val wasDeleted = useCase(unknownPostId)

        assertEquals(wasDeleted, false)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(1)
        )
    }

    @Test
    fun deleteMultipleUnknownPostId_shouldReturnFalse() = runTest {

        postRepository.sendPosts(testPosts)
        userRepository.toggleFavoritePostId(1, true)

        val unknownPostId = listOf(4,5)
        val wasDeleted = useCase(unknownPostId)

        assertEquals(wasDeleted, false)

        assertEquals(
            postRepository.getPosts().first(),
            testPosts
        )

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(1)
        )
    }



    private val testPosts = mutableListOf(
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