package com.voidx.myapplication.feature.login.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voidx.myapplication.feature.login.presentation.LoginViewModel
import com.voidx.myapplication.feature.login.presentation.model.LoginState

private typealias onValueChanged = (String) -> Unit

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    onSuccessfullyAuthenticated: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onSuccessfullyAuthenticated()
        }
    }

    LaunchedEffect(state.error) {
        state.error
            ?.takeIf { it.isNotBlank() }
            ?.run {
                snackbarHostState.showSnackbar(this)
                viewModel.resetError()
            }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        LoginScreenContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onLoginPressed = viewModel::login,
            onUserChanged = viewModel::onUserChanged,
            onPasswordChanged = viewModel::onPasswordChanged
        )
    }
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onLoginPressed: () -> Unit,
    onUserChanged: onValueChanged,
    onPasswordChanged: onValueChanged
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = state.user,
            onValueChange = onUserChanged,
            placeholder = {
                Text("type your login")
            },
        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 8.dp),
            value = state.password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = onPasswordChanged,
            placeholder = {
                Text("type your password")
            },
        )

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onLoginPressed
        ) {
            Text(text = "Login")
        }

    }
}

@Composable
@Preview
private fun LoginScreen_Preview() {
    LoginScreenContent(
        state = LoginState(),
        onLoginPressed = {},
        onUserChanged = {},
        onPasswordChanged = {}
    )
}