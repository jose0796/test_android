package com.example.data.fake

import com.example.core.model.UserData
import com.example.data.repository.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class TestUserRepository() : UserRepository {

    private val emptyUserData = UserData(favoritesPostsIds = emptySet())

    private val _userData = MutableSharedFlow<UserData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentUserData get() = _userData.replayCache.firstOrNull() ?: emptyUserData

    override val userData: Flow<UserData> = _userData.filterNotNull()

    override suspend fun toggleFavoritePostId(postId: Int, favorite: Boolean) {
        currentUserData.let { current ->
            val favoritePosts = if (favorite) current.favoritesPostsIds + postId
            else current.favoritesPostsIds - postId

            _userData.tryEmit(current.copy(favoritesPostsIds = favoritePosts))
        }
    }

}