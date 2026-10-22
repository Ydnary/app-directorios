package com.example.appdirectorios.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(user: FavoriteUser)

    @Query("SELECT * FROM favorite_users ORDER BY name ASC")
    fun getFavorites(): Flow<List<FavoriteUser>>

    @Query("DELETE FROM favorite_users WHERE id = :userId")
    suspend fun removeFavorite(userId: Int)

}