package com.example.feature.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetPostDetailUseCase
import com.example.domain.model.UserPostDetail
import com.test.core.common.result.Result
import com.test.core.common.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    val getPostsDetailUseCase: GetPostDetailUseCase
): ViewModel() {

    private val postId : MutableStateFlow<Int> = MutableStateFlow(-1)

    @OptIn(FlowPreview::class)
    val postDetailUiState : StateFlow<PostDetailUiState> =
        postId.flatMapConcat {
            if (it < 0) return@flatMapConcat flowOf(PostDetailUiState.Error)

            getPostsDetailUseCase(it)
                .asResult()
                .map { result ->
                    when(result) {
                        is Result.Success -> {
                            PostDetailUiState.Success(result.data)
                        }

                        is Result.Loading -> {
                            PostDetailUiState.Loading
                        }

                        is Result.Error -> {
                            PostDetailUiState.Error
                        }
                    }

                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PostDetailUiState.Loading
        )

    fun getDetail(id: Int) {
        postId.update { id }
    }
}

sealed interface PostDetailUiState {
    data class Success(val post: UserPostDetail) : PostDetailUiState
    object Error : PostDetailUiState
    object Loading : PostDetailUiState
}