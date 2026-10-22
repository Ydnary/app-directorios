package com.example.appdirectorios.data.mapper

import com.example.appdirectorios.data.local.FavoriteUser
import com.example.appdirectorios.data.model.Company
import com.example.appdirectorios.data.model.User

fun FavoriteUser.toUser(): User = User(
    id = id,
    name = name,
    email = email,
    company = Company(name = companyName)
)
