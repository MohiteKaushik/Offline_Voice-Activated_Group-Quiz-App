package com.example.quizz.models

data class Dialogue(
    val movieName: String,
    val resourceName: String? = null,
    val memeResourceName: String? = null,
    val variations: List<String> = emptyList()

)