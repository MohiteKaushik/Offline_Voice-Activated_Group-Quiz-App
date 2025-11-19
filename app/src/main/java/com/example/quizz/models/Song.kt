package com.example.quizz.models

data class Song(
    val movieName: String,
    val resourceName: String,  // e.g., "song_magadheera"
    val variations: List<String>  // Movie name variations for matching
)