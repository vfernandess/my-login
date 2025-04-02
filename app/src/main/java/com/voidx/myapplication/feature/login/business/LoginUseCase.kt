package com.voidx.myapplication.feature.login.business

import com.voidx.myapplication.feature.login.presentation.data.FailedLoginException

fun providesLoginUseCase(): LoginUseCase {
    return LoginUseCase.Impl()
}

interface LoginUseCase {

    operator fun invoke(user: String, password: String): Result<Boolean>

    class Impl: LoginUseCase {

        companion object {

            private const val ValidLogin = "email"
            private const val ValidPassword = "123456"

        }

        override fun invoke(user: String, password: String): Result<Boolean> {
            if (user.isBlank() || password.isBlank()) {
                return Result.failure(FailedLoginException.EmptyCredentials)
            }

            if (user != ValidLogin || password != ValidPassword) {
                return Result.failure(FailedLoginException.WrongCredentials)
            }

            return Result.success(true)
        }
    }
}