package com.example.quizz.models

data class MovieWithImage(
    val name: String,
    val imageResourceName: String,  // e.g., "movie_poster_baahubali"
    val variations: List<String>,
    val releaseYear: Int
)
