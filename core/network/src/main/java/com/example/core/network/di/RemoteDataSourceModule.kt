package com.example.core.network.di

import com.example.core.network.RemoteDataSource
import com.example.core.network.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ) : RemoteDataSource
}