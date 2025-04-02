package com.voidx.myapplication.feature.login.presentation.data

sealed class FailedLoginException: Exception() {

    data object EmptyCredentials: FailedLoginException()

    data object WrongCredentials: FailedLoginException()
}