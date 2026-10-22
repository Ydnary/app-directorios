package com.example.appdirectorios.data.repository

import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.data.model.Post
import com.example.appdirectorios.data.remote.RetrofitClient

class UserRepository {

    suspend fun getUsers(): List<User> {
        return RetrofitClient.apiService.getUsers()
    }

    suspend fun getPosts(userId: Int): List<Post> {
        return RetrofitClient.apiService.getPosts(userId)
    }

}