package com.example.core.database.di

import com.example.core.database.AppDatabase
import com.example.core.database.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesPostDao(
        database: AppDatabase,
    ): PostDao = database.postDao()

}
