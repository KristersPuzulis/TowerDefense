// Enemy.kt
// Šis fails definē Enemy klasi – objektus, kas attēlo pretiniekus spēlē. 
// Tajos tiek glabāta informācija par dzīvībām, atrašanās vietu un kustības progresu.

package com.example.towerdefense.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class Enemy(
    pathIndex: Int, // Kurā ceļa posmā atrodas pretinieks
    progress: Float, // Pretinieka progresa līmenis pa ceļu
    hp: Int, // Pretinieka hp skaits
    spawnDelay: Float // Kavēšanās laiks pirms parādīšanās uz trases
) {
    var pathIndex: Int = pathIndex
    // Atjaunināmi stāvokļi spēles laikā
    var progress by mutableFloatStateOf(progress)
    var hp by mutableIntStateOf(hp)
    var spawnDelay by mutableFloatStateOf(spawnDelay)

    // Pašreizējās koordinātas uz spēles kartes
    var x by mutableFloatStateOf(0f)
    var y by mutableFloatStateOf(0f)
}
