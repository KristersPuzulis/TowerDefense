package com.example.towerdefense.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.towerdefense.data.Tower
import com.example.towerdefense.R
import androidx.compose.foundation.layout.*

@Composable
fun TowerView(tower: Tower, cellSize: Dp) {
    val imageRes = when (tower.level) {
        1 -> R.drawable.tower_level1
        2 -> R.drawable.tower_level2
        3 -> R.drawable.tower_level3
        else -> R.drawable.tower_level1
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Tower level ${tower.level}",
        modifier = Modifier.size(cellSize)
    )
}

