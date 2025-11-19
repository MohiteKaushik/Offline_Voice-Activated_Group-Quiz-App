package com.example.quizz.models

data class Movie(
    val name: String,
    val year: Int,
    val variations: List<String>,
    val songResourceName: String? = null  // NEW: e.g., "song_magadheera"
)