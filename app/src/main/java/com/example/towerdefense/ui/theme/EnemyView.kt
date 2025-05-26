// EnemyView.kt
// Šis fails atbild par viena pretinieka attēlošanu spēles laukumā – ieskaitot tā pozīciju un dzīvības joslu.

package com.example.towerdefense.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import com.example.towerdefense.data.Enemy
import com.example.towerdefense.data.GameSettings
import kotlin.math.roundToInt

@Composable
fun EnemyView(enemy: Enemy, cellSize: Dp, indexOffset: Int = 0) {
    val density = LocalDensity.current

     // Aprēķina pretinieka atrašanās vietu starp diviem trases punktiem
    val start = GameSettings.path.getOrNull(enemy.pathIndex) ?: return
    val end = GameSettings.path.getOrNull(enemy.pathIndex + 1) ?: start

    val x = start.first + (end.first - start.first) * enemy.progress
    val y = start.second + (end.second - start.second) * enemy.progress

    val pixelOffset = with(density) { 4.dp.toPx() * indexOffset }

    // Aprēķina dzīvības joslas proporciju (0.0 līdz 1.0)
    val hpPercentage = enemy.hp.coerceIn(0, 100) / 100f
    val hpBarWidth = cellSize * 0.8f
    val hpBarHeight = 4.dp

    Box(
        modifier = Modifier
            .offset {
                // Pozicionē pretinieku uz ekrāna
                IntOffset(
                    x = (with(density) { cellSize.toPx() } * x + pixelOffset).roundToInt(),
                    y = (with(density) { cellSize.toPx() } * y + pixelOffset).roundToInt()
                )
            }
            .size(cellSize / 1.6f),
        contentAlignment = Alignment.TopCenter
    ) {
        // Dzīvības joslas sarkanā daļa
        Box(
            modifier = Modifier
                .offset(y = (-6).dp)
                .width(hpBarWidth)
                .height(hpBarHeight)
                .background(HPBarRed)
        )

        // Dzīvības joslas zaļā daļa
        Box(
            modifier = Modifier
                .offset(y = (-6).dp)
                .width(hpBarWidth * hpPercentage)
                .height(hpBarHeight)
                .background(HPBarGreen)
        )

        // Pretinieka izskats
        Text(
            text = "\uD83D\uDC7E", // 👾
            fontSize = 12.sp
        )
    }
}
