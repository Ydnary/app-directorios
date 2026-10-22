package com.example.appdirectorios.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appdirectorios.data.model.Post
import com.example.appdirectorios.data.model.User
import com.example.appdirectorios.ui.components.PostCard
import com.example.appdirectorios.ui.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    user: User,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: UserDetailViewModel = viewModel(key = "detail-${user.id}")
) {
    LaunchedEffect(user.id) {
        detailViewModel.loadPosts(user.id)
    }

    UserDetailContent(
        user = user,
        posts = detailViewModel.posts,
        isLoading = detailViewModel.isLoading,
        errorMessage = detailViewModel.errorMessage,
        onBack = onBack,
        onRetry = { detailViewModel.loadPosts(user.id) },
        modifier = modifier
    )
}

@Composable
private fun UserDetailContent(
    user: User,
    posts: List<Post>,
    isLoading: Boolean,
    errorMessage: String?,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Button(onClick = onBack) { Text("Volver") }
        }
        item { UserSummary(user = user) }
        item {
            Text(
                text = "Publicaciones",
                style = MaterialTheme.typography.titleLarge
            )
        }

        when {
            isLoading -> item { LoadingPosts() }
            errorMessage != null -> item {
                PostLoadError(message = errorMessage, onRetry = onRetry)
            }
            posts.isEmpty() -> item {
                Text("Este usuario no tiene publicaciones.")
            }
            else -> items(items = posts, key = { it.id }) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Composable
private fun UserSummary(user: User) {
    Column {
        Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Correo: ${user.email}")
        Text(text = "Empresa: ${user.company.name}")
    }
}

@Composable
private fun LoadingPosts() {
    Column {
        CircularProgressIndicator()
        Text("Cargando publicaciones...")
    }
}

@Composable
private fun PostLoadError(message: String, onRetry: () -> Unit) {
    Column {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetry) { Text("Reintentar") }
    }
}
