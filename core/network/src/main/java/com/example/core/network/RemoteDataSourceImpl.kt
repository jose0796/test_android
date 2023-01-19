package com.example.core.network

import com.example.core.network.model.AuthorDto
import com.example.core.network.model.CommentDto
import com.example.core.network.model.PostDto
import com.example.core.network.retrofit.RemoteApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val JSON_PlaceHolder_BaseUrl : String = BuildConfig.BASE_URL

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    moshi: Moshi
): RemoteDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(JSON_PlaceHolder_BaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()

        )
        .addConverterFactory(
            MoshiConverterFactory.create(moshi)
        )
        .build()
        .create(RemoteApi::class.java)


    override suspend fun getPosts(): List<PostDto> = networkApi.getPosts()
    override suspend fun getPostAuthor(id: Int): AuthorDto = networkApi.getPostAuthor(id)
    override suspend fun getPostComments(id: Int): List<CommentDto> = networkApi.getPostComments(id)

}