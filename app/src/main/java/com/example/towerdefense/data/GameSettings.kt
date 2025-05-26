// GameSettings.kt
// Šis fails satur globālos iestatījumus spēles laukuma izmēriem un pretinieku kustības ceļam.

package com.example.towerdefense.data

object GameSettings {
    // Spēles laukuma platums un augstums
    const val GRID_WIDTH = 10
    const val GRID_HEIGHT = 11

     // Ceļš, pa kuru pārvietojas pretinieki
    val path: List<Pair<Int, Int>> = buildList {
        for (i in 0 until 6) {
            val y = i * 2
            // Veido zig-zag ceļu pa līnijām
            if (i % 2 == 0) {
                for (x in 0 until GRID_WIDTH) add(Pair(x, y))
            } else {
                for (x in (GRID_WIDTH - 1) downTo 0) add(Pair(x, y))
            }
        }
        // Šo es uzrakstīju, kad sāku rakstīt kodu un tagad man nav skaidrības ko tas īsti dara, jo tika veiktas vairākas izmaiņas un tagad to noņemot kods nestrādā :)
        val turns = listOf(
            Pair(9, 1),
            Pair(0, 3),
            Pair(9, 5),
            Pair(0, 7),
            Pair(9, 9)
        )
        addAll(turns)
    }
}
