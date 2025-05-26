// Targeting.kt
// Tiek definēta stratēģija, kā tornis izvēlas mērķi starp pieejamajiem pretiniekiem.

package com.example.towerdefense.logic

import com.example.towerdefense.data.Enemy
import kotlin.math.sqrt

// Funkcionālais interfeiss, kas apraksta mērķēšanas loģiku
fun interface EnemyTargetingStrategy {
    fun selectTarget(towerX: Int, towerY: Int, enemies: List<Enemy>): Enemy?
}

// Noklusējuma mērķēšanas stratēģija, izvēlas tālāko pretinieku tornim pieejamajā rādiusā
fun defaultTargeting(range: Float): EnemyTargetingStrategy = EnemyTargetingStrategy { towerX, towerY, enemies ->
    enemies
        .filter { it.spawnDelay <= 0f } // Ignorē pretiniekus, kas vēl nav parādījušies
        .filter {
            val dx = towerX - it.x
            val dy = towerY - it.y
            sqrt(dx * dx + dy * dy) <= range // Atrodas torņa rādiusā
        }
        .maxByOrNull { it.pathIndex + it.progress } // Izvēlas to, kurš vistālāk ticis pa ceļu
}

