// GameEngine.kt
// Šis fails satur galveno spēles ciklu, kurā tiek atjaunināti pretinieku stāvokļi, torņi uzbrūk, un tiek pārvaldīta spēles gaita, viļņu maiņa un beigu scenāriji.

package com.example.towerdefense.logic

import androidx.navigation.NavController
import com.example.towerdefense.data.*
import com.example.towerdefense.data.GameSettings
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import androidx.compose.runtime.snapshots.SnapshotStateList
import android.content.Context
import android.media.MediaPlayer
import com.example.towerdefense.R

suspend fun updateGameLoop(
    enemies: MutableList<Enemy>,
    towers: SnapshotStateList<SnapshotStateList<Tower?>>,
    gameStarted: () -> Boolean,
    isPaused: () -> Boolean,
    currentScore: () -> Int,
    playerName: String,
    navController: NavController,
    updateScore: (Int) -> Unit,
    updateMoney: (Int) -> Unit,
    requestNextWave: () -> Unit,
    checkSpawnNewWave: () -> Boolean,
    currentWave: () -> Int,
    isFastForward: () -> Boolean,
    context: Context
) {
    val attackCooldown = 500f // Laiks starp torņu uzbrukuma cikliem
    var attackTimer = 0f
    var lastTime = System.nanoTime()

    while (true) {
        // Pārbauda vai spēle ir sākusies
        if (!gameStarted()) {
            println(">>> Game not started — exiting loop")
            break
        }
         // Ja spēle ir pauzēta, uz brīdi apstājas un atjauno laika skaitītāju
        if (isPaused()) {
            delay(100L)
            lastTime = System.nanoTime()
            continue
        }

        println(">>> Game loop is running")
        println("Enemy count: ${enemies.size}")

        val now = System.nanoTime()
        val rawDelta = (now - lastTime) / 1_000_000f
        val deltaTime = if (isFastForward()) rawDelta * 2f else rawDelta
        lastTime = now

        // Noņem pretiniekus, kuriem ir beigušies dzīvības
        enemies.removeAll { it.hp <= 0 }

        // Atjaunina katra pretinieka stāvokli
        enemies.forEach { enemy ->
            println(">>> Enemy before: pathIndex=${enemy.pathIndex}, progress=${enemy.progress}, delay=${enemy.spawnDelay}, hp=${enemy.hp}")
            enemy.spawnDelay -= deltaTime
            if (enemy.spawnDelay > 0f) return@forEach

            enemy.progress += 0.002f * deltaTime

            // Aprēķina pretinieka atrašanās vietu starp diviem punktiem
            val currentIndex = enemy.pathIndex
            val nextIndex = currentIndex + 1

            if (nextIndex < GameSettings.path.size) {
                val (x1, y1) = GameSettings.path[currentIndex]
                val (x2, y2) = GameSettings.path[nextIndex]
                enemy.x = x1 + (x2 - x1) * enemy.progress
                enemy.y = y1 + (y2 - y1) * enemy.progress
            }

            // Kad progress sasniedz 1.0, pretinieks pārlec uz nākamo punktu
            if (enemy.progress >= 1f) {
                enemy.progress = 0f
                enemy.pathIndex++
                println(">>> Enemy advanced to pathIndex ${enemy.pathIndex}")
                if (enemy.pathIndex >= GameSettings.path.lastIndex) {
                    println(">>> Enemy reached end — game over")
                    // Pretinieks sasniedz beigas → spēlētājs zaudē
                    ScoreManager.addScore(playerName, currentScore())
                    navController.navigate("result/You Lose!")
                    return
                }
            }
        }

        // Laiks torņiem uzbrukt, ja cooldown ir pagājis
        attackTimer += deltaTime
        if (attackTimer >= attackCooldown) {
            attackTimer = 0f

            val hitEnemies = mutableSetOf<Enemy>()

            for (row in towers.indices) {
                for (col in towers[row].indices) {
                    val tower = towers[row][col] ?: continue

                    // Atrod tuvāko pretinieku, kas vēl nav trāpīts šajā ciklā
                    val target = enemies
                        .filter { it.spawnDelay <= 0f && it !in hitEnemies }
                        .minByOrNull {
                            val dx = col - it.x
                            val dy = row - it.y
                            sqrt(dx * dx + dy * dy)
                        }

                    target?.let {
                        val dx = col - it.x
                        val dy = row - it.y
                        val distance = sqrt(dx * dx + dy * dy)

                        if (distance <= 1.5f) {
                            // Pretiniekam atņemas hp
                            val damage = 20 * tower.level
                            it.hp -= damage
                            MediaPlayer.create(context, R.raw.shoot).start()
                            hitEnemies.add(it)
                            println(">>> Tower at ($col,$row) hit enemy id=${it.hashCode()} with $damage dmg")
                            if (it.hp <= 0) {
                                updateScore(+1)
                                updateMoney(+50)
                                MediaPlayer.create(context, R.raw.enemy_die).start()
                            }
                        }
                    }
                }
            }
        }

        // Ja pienācis laiks nākamajam vilnim
        if (checkSpawnNewWave()) {
            if (currentWave() >= 10) {
                println(">>> All waves completed — You Win!")
                // Spēle beidzas ar uzvaru
                ScoreManager.addScore(playerName, currentScore())
                navController.navigate("result/You Win!")
                return
            } else {
                println(">>> Spawning next wave...")
                // Ievieto kavēšanos pirms nākamā viļņa
                delay(2000L)
                requestNextWave()
            }
        }
        // Cikla aizture, lai spēle netiktu atjaunināta pārāk bieži
        delay(16L)
    }
}
