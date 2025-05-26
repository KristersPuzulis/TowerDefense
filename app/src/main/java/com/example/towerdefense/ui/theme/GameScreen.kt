// GameScreen.kt
// Šis fails attēlo galveno spēles ekrānu, kur lietotājs redz spēles laukumu, var izvietot torņus, sekot rezultātam un kontrolēt spēles gaitu.

package com.example.towerdefense.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.towerdefense.data.Enemy
import com.example.towerdefense.data.GameSettings.GRID_HEIGHT
import com.example.towerdefense.data.GameSettings.GRID_WIDTH
import com.example.towerdefense.data.GameSettings.path
import com.example.towerdefense.data.Tower
import com.example.towerdefense.logic.updateGameLoop
import com.example.towerdefense.R
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext

// Datu klase, kas satur viļņa informāciju
data class Wave(
    val number: Int,
    val enemyCount: Int,
    val hp: Int,
    val spawnDelayStep: Float
)

// Ģenerē viļņa datus atkarībā no kārtas numura
fun generateWave(number: Int): Wave {
    return Wave(
        number = number,
        enemyCount = 3 + number * 2,
        hp = 100 + (number - 1) * 20,
        spawnDelayStep = (2000f - number * 100).coerceAtLeast(500f)
    )
}

@Composable
fun GameScreen(playerName: String, navController: NavController) {
    // Spēles stāvokļa mainīgie
    val enemies = remember { mutableStateListOf<Enemy>() }
    val context = LocalContext.current

    val towers = remember {
        mutableStateListOf(
            *Array(GRID_HEIGHT) { MutableList<Tower?>(GRID_WIDTH) { null } }
                .map { it.toMutableStateList() }.toTypedArray()
        )
    }

    var score by remember { mutableIntStateOf(0) }
    var money by remember { mutableIntStateOf(400) }
    var gameStarted by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var waveNumber by remember { mutableIntStateOf(1) }
    var isFastForward by remember { mutableStateOf(false) }

    // Aprēķina šūnas izmēru atkarībā no ekrāna lieluma
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val maxGridWidth = screenWidth / GRID_WIDTH
    val maxGridHeight = (screenHeight - 160.dp) / GRID_HEIGHT
    val cellSize = minOf(maxGridWidth, maxGridHeight)

    // Ģenerē pretiniekus jaunam vilnim, kad tas sākas
    LaunchedEffect(gameStarted, waveNumber) {
        if (gameStarted && enemies.isEmpty()) {
            val wave = generateWave(waveNumber)
            repeat(wave.enemyCount) { i ->
                enemies.add(Enemy(0, 0f, wave.hp, i * wave.spawnDelayStep))
            }
        }
    }

    // Uzsāk spēles loģiku, kad spēle sākas
    LaunchedEffect(gameStarted) {
        if (gameStarted) {
            updateGameLoop(
                enemies = enemies,
                towers = towers,
                gameStarted = { gameStarted },
                isPaused = { isPaused },
                currentScore = { score },
                playerName = playerName,
                navController = navController,
                updateScore = { delta -> score += delta },
                updateMoney = { delta -> money += delta },
                currentWave = { waveNumber },
                isFastForward = { isFastForward },
                context = context,
                requestNextWave = {
                    waveNumber++
                    enemies.clear()

                    if (waveNumber > 10) {
                        gameStarted = false
                        navController.navigate("result/${playerName}/$score")
                    } else {
                        val wave = generateWave(waveNumber)
                        repeat(wave.enemyCount) { i ->
                            enemies.add(Enemy(0, 0f, wave.hp, i * wave.spawnDelayStep))
                        }
                    }
                },
                checkSpawnNewWave = { enemies.isEmpty() && waveNumber <= 10 }
            )
        }
    }

    // UI izkārtojums
    Column(modifier = Modifier.fillMaxSize()) {

        // Augšējā josla ar rezultātu un pogām
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Player: $playerName | Wave: $waveNumber | Score: $score | Money: $$money")

            Row {
                Button(onClick = { gameStarted = true }, enabled = !gameStarted) {
                    Text("Start Game")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { isPaused = !isPaused }, enabled = gameStarted) {
                    Text(if (isPaused) "Resume" else "Pause")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { isFastForward = !isFastForward }, enabled = gameStarted) {
                    Text(if (isFastForward) "2x Speed ON" else "2x Speed OFF")
                }
            }
        }

        // Spēles laukums ar torņiem, zāli, ceļu un pretiniekiem
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column {
                for (row in 0 until GRID_HEIGHT) {
                    Row {
                        for (col in 0 until GRID_WIDTH) {
                            val tower = towers[row][col]
                            val isPath = path.contains(Pair(col, row))
                            val isTower = tower != null

                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .clickable {
                                        val tower = towers[row][col]

                                        if (tower != null) {
                                            val upgradeCost = when (tower.level) {
                                                1 -> 100
                                                2 -> 150
                                                else -> Int.MAX_VALUE
                                            }

                                            if (tower.level < 3 && money >= upgradeCost) {
                                                tower.level += 1
                                                money -= upgradeCost
                                                MediaPlayer.create(context, R.raw.upgrade_tower).start()
                                            }

                                        // Ja tukšs zāliens – uzliek torni
                                        } else if (!isPath && money >= 100) {
                                            towers[row][col] = Tower(x = col, y = row).apply { level = 1 }
                                            money -= 100
                                            MediaPlayer.create(context, R.raw.place_tower).start()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    isTower -> {
                                        TowerView(tower = tower!!, cellSize = cellSize)
                                    }
                                    isPath -> {
                                        Box(modifier = Modifier.fillMaxSize().background(PathColor))
                                    }
                                    else -> {
                                        Box(modifier = Modifier.fillMaxSize().background(GrassColor))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Zīmē pretiniekus
            enemies
                .filter { it.spawnDelay <= 0f }
                .forEachIndexed { index, enemy ->
                    EnemyView(enemy = enemy, cellSize = cellSize, indexOffset = index % 3)
                }
        }
    }
}
