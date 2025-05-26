package com.example.towerdefense.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class Enemy(
    pathIndex: Int,
    progress: Float,
    hp: Int,
    spawnDelay: Float
) {
    var pathIndex: Int = pathIndex
    var progress by mutableFloatStateOf(progress)
    var hp by mutableIntStateOf(hp)
    var spawnDelay by mutableFloatStateOf(spawnDelay)

    var x by mutableFloatStateOf(0f)
    var y by mutableFloatStateOf(0f)
}