// Tower.kt
// Šis fails definē torni

package com.example.towerdefense.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Tower(
    val x: Int, // Torņa koardinātes
    val y: Int, // Torņa koardinātes
    var range: Float = 1.5f, // Torņa uzbrukuma rādiuss
    var cooldown: Float = 0f // Laiks starp šāvieniem
) {
    // Torņa līmenis
    var level by mutableStateOf(1)
}
