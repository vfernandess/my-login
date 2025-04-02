package com.voidx.myapplication.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.voidx.myapplication.feature.login.business.LoginUseCase
import com.voidx.myapplication.feature.login.business.providesLoginUseCase
import com.voidx.myapplication.feature.login.presentation.data.FailedLoginException
import com.voidx.myapplication.feature.login.presentation.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    loginState: LoginState = LoginState(),
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val stateFlow = MutableStateFlow(loginState)

    val state: StateFlow<LoginState>
        get() = stateFlow

    fun login() {
        val (user, password) = stateFlow.value
        val result = loginUseCase(user, password)

        result
            .onFailure {
                val error = it as? FailedLoginException
                when (error) {
                    FailedLoginException.EmptyCredentials ->
                        onErrorChanged("No user or password were provided.")

                    FailedLoginException.WrongCredentials ->
                        onErrorChanged("Wrong user or password.")

                    null ->
                        onErrorChanged("Something went wrong.")
                }
            }
            .onSuccess {
                resetError()

                val newState = state.value.copy(isAuthenticated = true)
                changeState(newState)
            }
    }

    private fun onErrorChanged(newError: String) {
        val newState = stateFlow.value.copy(error = newError)
        changeState(newState)
    }

    fun resetError() {
        val newState = stateFlow.value.copy(error = NO_ERROR)
        changeState(newState)
    }

    fun onPasswordChanged(newPassword: String) {
        val newState = stateFlow.value.copy(password = newPassword)
        changeState(newState)
    }

    fun onUserChanged(newUser: String) {
        val newState = stateFlow.value.copy(user = newUser)
        changeState(newState)
    }

    private fun changeState(newState: LoginState) {
        stateFlow.tryEmit(newState)
    }

    // Define ViewModel factory in a companion object
    companion object {

        private const val NO_ERROR = ""

        fun providesLoginViewModel(
            loginUseCase: LoginUseCase = providesLoginUseCase()
        ): LoginViewModel {
            return LoginViewModel(
                loginUseCase = loginUseCase
            )
        }

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Get the dependency in your factory
                providesLoginViewModel()
            }
        }
    }
}