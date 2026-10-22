package com.example.appdirectorios.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdirectorios.data.local.AppDatabase
import com.example.appdirectorios.data.local.FavoriteUser
import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.data.repository.FavoriteRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = FavoriteRepository(
        AppDatabase.getDatabase(application).favoriteUserDao()
    )

    var favorites by mutableStateOf<List<FavoriteUser>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            repository.getFavorites()
                .catch {
                    errorMessage = "No se pudieron leer los favoritos."
                }
                .collect { savedFavorites ->
                    favorites = savedFavorites
                }
        }
    }

    fun isFavorite(userId: Int): Boolean {
        return favorites.any { it.id == userId }
    }

    fun addFavorite(user: User) {
        viewModelScope.launch {
            errorMessage = null

            try {
                repository.addFavorite(user)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                errorMessage = "No se pudo guardar el favorito."
            }
        }
    }

    fun removeFavorite(userId: Int) {
        viewModelScope.launch {
            errorMessage = null

            try {
                repository.removeFavorite(userId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                errorMessage = "No se pudo quitar el favorito."
            }
        }
    }
}