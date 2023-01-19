package com.example.domain

import com.example.data.repository.PostRepository
import javax.inject.Inject

class LoadPostsFromRemoteUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke() =
        postRepository.loadPostsFromRemote()
}