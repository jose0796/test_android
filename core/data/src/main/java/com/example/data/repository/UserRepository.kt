package com.example.data.repository

import com.example.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    /**
     * Toggles the user's newly favorite/unfavorite posts
     */
    suspend fun toggleFavoritePostId(postId: Int, favorite: Boolean)


}