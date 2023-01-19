package com.example.domain

import com.example.data.repository.UserRepository
import javax.inject.Inject

class ToggleFavoritePostUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(postId: Int, favorite: Boolean) =
        userRepository.toggleFavoritePostId(postId, favorite)
}