package com.example.domain

import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.domain.model.UserPost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userDataRepository: UserRepository
){

    operator fun invoke() : Flow<List<UserPost>> =
        combine(
            userDataRepository.userData,
            postRepository.getPosts()
        ) { userData, posts ->
            posts.map { post ->
                UserPost(
                    id = post.id,
                    userId = post.userId,
                    title = post.title,
                    body = post.body,
                    favorite = post.id in userData.favoritesPostsIds
                )
            }
        }

}