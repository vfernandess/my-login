package com.voidx.myapplication.feature.login.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val user: String = "",
    val password: String = "",
    val isAuthenticated: Boolean = false,
    val error: String? = null
)
