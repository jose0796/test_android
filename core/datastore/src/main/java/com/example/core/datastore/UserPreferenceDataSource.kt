package com.example.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.core.model.UserData
import com.test.example.core.datastore.UserPreferences
import com.test.example.core.datastore.copy
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData = userPreferences.data
        .map {
            UserData(
                favoritesPostsIds = it.favoritesPostsMap.keys
            )
        }

    suspend fun toggleFavoriteTopicId(postId: Int, followed: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    if (followed) {
                        favoritesPosts.put(postId, true)
                    } else {
                        favoritesPosts.remove(postId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("TestPreferences", "Failed to update user preferences", ioException)
        }
    }

}