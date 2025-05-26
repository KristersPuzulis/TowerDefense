package com.example.towerdefense

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.towerdefense.ui.theme.GameScreen
import com.example.towerdefense.ui.theme.ResultScreen
import com.example.towerdefense.ui.theme.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("game/{playerName}") { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"
            GameScreen(playerName, navController)
        }
        composable("result/{resultMessage}") { backStackEntry ->
            val resultMessage = backStackEntry.arguments?.getString("resultMessage") ?: "Result"
            ResultScreen(resultMessage, navController)
        }
    }
}