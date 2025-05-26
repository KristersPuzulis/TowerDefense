package com.example.towerdefense.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    var playerName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome! Enter your name:")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (playerName.isNotEmpty()) {
                    navController.navigate("game/$playerName")
                }
            }
        ) {
            Text(text = "Start Game")
        }
    }
}