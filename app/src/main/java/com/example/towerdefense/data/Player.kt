// Player.kt
// Šis fails definē spēlētāja datus – vārdu un iegūto punktu skaitu.

package com.example.towerdefense.data

// Datu klase, kas reprezentē vienu spēlētāju
data class Player(
    val name: String, // Spēlētāja ievadītais vārds
    var score: Int = 0  // Spēles laikā iegūto punktu skaits
)
