package com.example.quizz.models

data class GameConfig(
    val teams: List<Team>,
    var currentTeamIndex: Int = 0,
    val category: GameMode,
    val micMode: MicMode,
    val usedAnswers: MutableSet<String> = mutableSetOf(),
    val winningScore: Int = 20
)