// Navigation.kt
// Šis fails nodrošina navigāciju starp spēles ekrāniem - sākuma ekrānu, spēli un rezultātu ekrānu.

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
    // Izveido navigācijas kontrolieri, kas atbild par ekrānu pārslēgšanu
    val navController = rememberNavController()

     // Definē maršrutus starp dažādiem ekrāniem lietotnē
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // Maršruts uz sākuma ekrānu
        composable("welcome") {
            WelcomeScreen(navController)
        }
        // Maršruts uz spēles ekrānu, kur tiek nodots spēlētāja vārds
        composable("game/{playerName}") { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"
            GameScreen(playerName, navController)
        }
        // Maršruts uz rezultātu ekrānu ar ziņojumu par spēles iznākumu
        composable("result/{resultMessage}") { backStackEntry ->
            val resultMessage = backStackEntry.arguments?.getString("resultMessage") ?: "Result"
            ResultScreen(resultMessage, navController)
        }
    }
}
