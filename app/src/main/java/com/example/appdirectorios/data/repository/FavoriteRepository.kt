package com.example.appdirectorios.data.repository

import com.example.appdirectorios.data.local.FavoriteUser
import com.example.appdirectorios.data.local.FavoriteUserDao
import com.example.appdirectorios.data.model.User
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(
    private val dao: FavoriteUserDao
) {

    fun getFavorites(): Flow<List<FavoriteUser>> {
        return dao.getFavorites()
    }

    suspend fun addFavorite(user: User) {
        val favorite = FavoriteUser(
            id = user.id,
            name = user.name,
            email = user.email,
            companyName = user.company.name
        )

        dao.addFavorite(favorite)
    }

    suspend fun removeFavorite(userId: Int) {
        dao.removeFavorite(userId)
    }
}