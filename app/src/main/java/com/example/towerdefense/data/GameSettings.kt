package com.example.towerdefense.data

object GameSettings {
    const val GRID_WIDTH = 10
    const val GRID_HEIGHT = 11

    val path: List<Pair<Int, Int>> = buildList {
        for (i in 0 until 6) {
            val y = i * 2
            if (i % 2 == 0) {
                for (x in 0 until GRID_WIDTH) add(Pair(x, y))
            } else {
                for (x in (GRID_WIDTH - 1) downTo 0) add(Pair(x, y))
            }
        }

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