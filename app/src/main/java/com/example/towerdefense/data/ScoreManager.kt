// ScoreManager.kt
// Saglabā visus spēlētāju rezultātus 

package com.example.towerdefense.data

object ScoreManager {
    // Saraksts, kurā tiek uzglabāti pāri - spēlētāja vārds un rezultāts
    private val scores = mutableListOf<Pair<String, Int>>()

    // Pievieno jaunu rezultātu sarakstam
    fun addScore(playerName: String, score: Int) {
        scores.add(playerName to score)
    }

    // Atgriež visus rezultātus
    fun getAllScores(): List<Pair<String, Int>> = scores
}
