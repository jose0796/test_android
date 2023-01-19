package com.example.data.repository

import com.example.core.datastore.UserPreferenceDataSource
import com.example.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : UserRepository {

    override val userData: Flow<UserData>
        get() = userPreferenceDataSource.userData

    override suspend fun toggleFavoritePostId(postId: Int, favorite: Boolean) =
        userPreferenceDataSource.toggleFavoriteTopicId(postId, favorite)
}