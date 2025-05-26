// ResultScreen.kt
// Šis fails attēlo spēles beigu ekrānu ar rezultāta ziņojumu un pogu spēles restartēšanai.

package com.example.towerdefense.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(resultMessage: String, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Parāda uzvaras vai zaudējuma ziņu
        Text(text = resultMessage, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        // Poga, kas atgriež uz sākuma ekrānu
        Button(onClick = { navController.navigate("welcome") }) {
            Text(text = "Restart")
        }
    }
}
