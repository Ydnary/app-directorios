package com.example.appdirectorios.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val companyName: String
)