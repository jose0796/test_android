package com.example.feature.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeletePostUseCase
import com.example.domain.GetPostsUseCase
import com.example.domain.LoadPostsFromRemoteUseCase
import com.example.domain.ToggleFavoritePostUseCase
import com.example.domain.model.UserPost
import com.test.core.common.result.Result
import com.test.core.common.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val toggleFavoritePostUseCase: ToggleFavoritePostUseCase,
    val deletePostUseCase: DeletePostUseCase,
    val loadPostsFromRemoteUseCase: LoadPostsFromRemoteUseCase
) : ViewModel() {

    val postsUiState : StateFlow<PostsUiState> =
        getPostsUseCase()
            .asResult()
            .map { result ->
                when(result) {
                    is Result.Success -> {
                        val favoritePosts = result.data.filter { it.favorite }
                        val regularPosts = result.data.filter { !it.favorite }
                        PostsUiState.Success(favoritePosts + regularPosts)
                    }

                    is Result.Loading -> {
                        PostsUiState.Loading
                    }

                    is Result.Error -> {
                        PostsUiState.Error
                    }
                }

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PostsUiState.Loading
            )

    fun toggleFavorite(postId: Int, favorite: Boolean) =
        viewModelScope.launch {
            toggleFavoritePostUseCase(postId, favorite)
        }

    fun deletePost(postId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deletePostUseCase(postId)
        }

    fun deleteAllRegularPosts() =
        viewModelScope.launch(Dispatchers.IO) {
            val posts = getPostsUseCase().first()
            val regularPosts = posts.filter { !it.favorite }
            deletePostUseCase(regularPosts.map { it.id })
        }

    fun loadAllFromRemote() =
        viewModelScope.launch {
            loadPostsFromRemoteUseCase()
        }
}

sealed interface PostsUiState {
    data class Success(val posts: List<UserPost>) : PostsUiState
    object Error : PostsUiState
    object Loading : PostsUiState
}