package com.example.towerdefense.logic

import com.example.towerdefense.data.Enemy
import kotlin.math.sqrt

fun interface EnemyTargetingStrategy {
    fun selectTarget(towerX: Int, towerY: Int, enemies: List<Enemy>): Enemy?
}

fun defaultTargeting(range: Float): EnemyTargetingStrategy = EnemyTargetingStrategy { towerX, towerY, enemies ->
    enemies
        .filter { it.spawnDelay <= 0f }
        .filter {
            val dx = towerX - it.x
            val dy = towerY - it.y
            sqrt(dx * dx + dy * dy) <= range
        }
        .maxByOrNull { it.pathIndex + it.progress }
}

