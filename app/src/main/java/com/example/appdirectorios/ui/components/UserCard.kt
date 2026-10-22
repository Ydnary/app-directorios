package com.example.appdirectorios.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdirectorios.data.model.User

@Composable
fun UserCard(
    user: User,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.titleMedium)
            Text(text = user.email)
            Text(text = user.company.name)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onFavoriteClick) {
                Text(
                    text = if (isFavorite) {
                        "Quitar de favoritos"
                    } else {
                        "Guardar en favoritos"
                    }
                )
            }
        }
    }
}
