package com.example.domain

import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.domain.mapper.asPresentationModel
import com.example.domain.model.UserPost
import com.example.domain.model.UserPostDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPostDetailUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {

    operator fun invoke(postId: Int) : Flow<UserPostDetail> =
        combine(
            userRepository.userData,
            postRepository.getPostDetail(postId)
        ) { userData, postDetail ->
            UserPostDetail(
                userPost =
                    UserPost(
                        id = postDetail.post.id,
                        userId = postDetail.post.userId,
                        title = postDetail.post.title,
                        body = postDetail.post.body,
                        favorite = postDetail.post.id in userData.favoritesPostsIds
                    ),
                author = postDetail.author?.asPresentationModel(),
                comments = postDetail.comments.map { it.asPresentationModel() },
                favorite = postDetail.post.id in userData.favoritesPostsIds
            )

        }

}