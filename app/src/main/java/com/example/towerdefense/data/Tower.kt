package com.example.towerdefense.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Tower(
    val x: Int,
    val y: Int,
    var range: Float = 1.5f,
    var cooldown: Float = 0f
) {
    var level by mutableStateOf(1)
}