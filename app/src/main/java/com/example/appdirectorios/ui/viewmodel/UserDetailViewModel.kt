package com.example.appdirectorios.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdirectorios.data.model.Post
import com.example.appdirectorios.data.repository.UserRepository
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {

    private val repository = UserRepository()

    var posts by mutableStateOf<List<Post>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadPosts(userId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            posts = emptyList()

            try {
                posts = repository.getPosts(userId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                errorMessage =
                    "No se pudo conectar. Revisa tu conexión a Internet."
            } catch (e: Exception) {
                errorMessage =
                    "No se pudieron cargar las publicaciones. Inténtalo de nuevo."
            } finally {
                isLoading = false
            }
        }
    }
}