package com.example.appdirectorios.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appdirectorios.data.mapper.toUser
import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.ui.components.UserCard
import com.example.appdirectorios.ui.viewmodel.FavoriteViewModel
import com.example.appdirectorios.ui.viewmodel.UserViewModel

@Composable
fun UserListScreen(
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val users = if (userViewModel.errorMessage != null) {
        favoriteViewModel.favorites.map { it.toUser() }
    } else {
        userViewModel.users
    }

    UserListContent(
        users = users,
        favoriteIds = favoriteViewModel.favorites.map { it.id }.toSet(),
        isLoading = userViewModel.isLoading,
        connectionError = userViewModel.errorMessage,
        favoriteError = favoriteViewModel.errorMessage,
        onRetry = userViewModel::loadUsers,
        onUserClick = onUserClick,
        onToggleFavorite = { user ->
            if (favoriteViewModel.isFavorite(user.id)) {
                favoriteViewModel.removeFavorite(user.id)
            } else {
                favoriteViewModel.addFavorite(user)
            }
        },
        modifier = modifier
    )
}

@Composable
private fun UserListContent(
    users: List<User>,
    favoriteIds: Set<Int>,
    isLoading: Boolean,
    connectionError: String?,
    favoriteError: String?,
    onRetry: () -> Unit,
    onUserClick: (User) -> Unit,
    onToggleFavorite: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Directorio de usuarios",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (favoriteError != null) {
            Text(text = favoriteError, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (connectionError != null) {
            ConnectionNotice(message = connectionError, onRetry = onRetry)
        }

        when {
            isLoading -> LoadingUsers()
            users.isEmpty() -> Text(
                text = if (connectionError != null) {
                    "No tienes favoritos guardados para consultar sin conexión."
                } else {
                    "No hay usuarios para mostrar."
                }
            )
            else -> UsersList(
                users = users,
                favoriteIds = favoriteIds,
                onUserClick = onUserClick,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
private fun UsersList(
    users: List<User>,
    favoriteIds: Set<Int>,
    onUserClick: (User) -> Unit,
    onToggleFavorite: (User) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items = users, key = { it.id }) { user ->
            UserCard(
                user = user,
                isFavorite = user.id in favoriteIds,
                onClick = { onUserClick(user) },
                onFavoriteClick = { onToggleFavorite(user) }
            )
        }
    }
}

@Composable
private fun ConnectionNotice(message: String, onRetry: () -> Unit) {
    Column {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Text("Mostrando favoritos guardados en el teléfono.")
        Button(onClick = onRetry) {
            Text("Reintentar conexión")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun LoadingUsers() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
