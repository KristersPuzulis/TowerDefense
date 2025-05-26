package com.example.towerdefense.data

object ScoreManager {
    private val scores = mutableListOf<Pair<String, Int>>()

    fun addScore(playerName: String, score: Int) {
        scores.add(playerName to score)
    }

    fun getAllScores(): List<Pair<String, Int>> = scores
}