package com.example.quizz.models

data class Hero(
    val name: String,
    val imageResourceName: String,  // e.g., "hero_mahesh_babu"
    val movies: List<Movie>,
    val aliases: List<String>  // Different name variations
)
