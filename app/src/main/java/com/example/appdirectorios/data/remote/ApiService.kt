package com.example.appdirectorios.data.remote

import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: Int
    ): List<Post>
}