package com.example.domain

import com.example.data.fake.TestUserRepository
import com.example.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleFavoritePostUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository = TestUserRepository()

    val useCase = ToggleFavoritePostUseCase(
        userRepository
    )

    @Test
    fun toggleFavoriteTrue_shouldStoreFavoriteId() = runTest {

        val favoriteSelectedPostId = 1
        useCase(favoriteSelectedPostId, true)

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(favoriteSelectedPostId)
        )
    }

    @Test
    fun toggleFavoriteFalse_shouldNotStoreFavoriteId() = runTest {

        val favoriteSelectedPostId = 1
        useCase(favoriteSelectedPostId, false)

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            emptySet<Int>()
        )
    }

    @Test
    fun toggleFavoriteMultipleTimes_shouldRemainConsistent() = runTest {

        val favoriteSelectedPostId = 1
        useCase(favoriteSelectedPostId, true)

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(favoriteSelectedPostId)
        )

        useCase(favoriteSelectedPostId, true)

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            setOf(favoriteSelectedPostId)
        )

        useCase(favoriteSelectedPostId, false)

        assertEquals(
            userRepository.userData.first().favoritesPostsIds,
            emptySet<Int>()
        )
    }

}