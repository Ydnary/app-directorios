package com.example.appdirectorios.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.data.repository.UserRepository
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    var users by mutableStateOf<List<User>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                users = repository.getUsers()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                errorMessage =
                    "No se pudo conectar. Revisa tu conexión a Internet."
            } catch (e: Exception) {
                errorMessage =
                    "No se pudieron cargar los usuarios. Inténtalo de nuevo."
            } finally {
                isLoading = false
            }
        }
    }
}