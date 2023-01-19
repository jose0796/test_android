package com.example.domain

import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(postId: Int) : Boolean {
        val userData = userRepository.userData.first()
        if (postId in userData.favoritesPostsIds) {
            userRepository.toggleFavoritePostId(postId, false)
        }
        return postRepository.deletePost(postId)
    }

    suspend operator fun invoke(postIds: List<Int>) : Boolean {
        val userData = userRepository.userData.first()
        postIds.forEach {id ->
            if (id in userData.favoritesPostsIds) {
                userRepository.toggleFavoritePostId(id, false)
            }
        }

       return postRepository.deletePosts(postIds)
    }





}