package com.example.quizz.models

data class GameState(
    val teams: List<Team>,
    var currentTeamIndex: Int = 0,
    val gameMode: String,  // Changed from category String
    val usedMovies: MutableSet<String> = mutableSetOf(),
    val winningScore: Int = 20
)