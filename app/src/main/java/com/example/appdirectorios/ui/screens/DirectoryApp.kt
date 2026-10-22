package com.example.appdirectorios.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appdirectorios.data.mapper.toUser
import com.example.appdirectorios.ui.viewmodel.FavoriteViewModel
import com.example.appdirectorios.ui.viewmodel.UserViewModel

@Composable
fun DirectoryApp(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    var selectedUserId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    val selectedUser = userViewModel.users.find {
        it.id == selectedUserId
    } ?: favoriteViewModel.favorites.find {
        it.id == selectedUserId
    }?.toUser()

    BackHandler(enabled = selectedUser != null) {
        selectedUserId = null
    }

    if (selectedUser == null) {
        UserListScreen(
            modifier = modifier,
            userViewModel = userViewModel,
            favoriteViewModel = favoriteViewModel,
            onUserClick = { user ->
                selectedUserId = user.id
            }
        )
    } else {
        UserDetailScreen(
            user = selectedUser,
            modifier = modifier,
            onBack = {
                selectedUserId = null
            }
        )
    }
}
