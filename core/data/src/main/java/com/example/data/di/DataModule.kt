package com.example.data.di

import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPostRepository(
        postRepository: PostRepositoryImpl
    ): PostRepository

    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

}