package com.voidx.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.voidx.myapplication.feature.login.view.LoginScreen
import com.voidx.myapplication.feature.welcome.view.WelcomeScreen
import com.voidx.myapplication.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {

                    composable("login") {
                        LoginScreen {
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo("login", true)
                                .build()
                            navController.navigate("welcome", navOptions = navOptions)
                        }
                    }

                    composable("welcome") {
                        WelcomeScreen()
                    }
                }
            }
        }
    }
}