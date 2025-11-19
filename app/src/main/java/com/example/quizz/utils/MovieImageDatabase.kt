package com.example.quizz.utils

import com.example.quizz.models.MovieWithImage

class MovieImageDatabase {

    private val movies = mutableListOf<MovieWithImage>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        movies.addAll(listOf(
            // Add movie posters here
            // Images should be in res/drawable/ folder
            // Name format: movie_poster_[name].jpg or .png

            MovieWithImage(
                name = "24",
                imageResourceName = "movie_poster_24",
                variations = listOf("Twenty Fourr", "24"),
                releaseYear = 2016
            ),

            MovieWithImage(
                name = "Aarya2",
                imageResourceName = "movie_poster_aarya2",
                variations = listOf("aarya", "arya","aarya2","arya2"),
                releaseYear = 2005
            ),
            MovieWithImage(
                name = "Athadu",
                imageResourceName = "movie_poster_athadu",
                variations = listOf("athadu", "atadu", "attadu"),
                releaseYear = 2005
            ),
            MovieWithImage(
                name = "Arundhati",
                imageResourceName = "movie_poster_arundati",
                variations = listOf("arundhati", "arundati"),
                releaseYear = 2009
            ),
            MovieWithImage(
                name = "Awara",
                imageResourceName = "movie_poster_awara",
                variations = listOf("awara", "paiyaa"),
                releaseYear = 2010
            ),
            MovieWithImage(
                name = "Baahubali",
                imageResourceName = "movie_poster_baahubali",
                variations = listOf("baahubali", "bahubali", "baahubali 1"),
                releaseYear = 2015
            ),
            MovieWithImage(
                name = "Bharat Ane Nenu",
                imageResourceName = "movie_poster_baratanenenu",
                variations = listOf("bharat ane nenu", "bharat", "barat"),
                releaseYear = 2018
            ),
            MovieWithImage(
                name = "Businessman",
                imageResourceName = "movie_poster_businessman",
                variations = listOf("businessman", "business man"),
                releaseYear = 2012
            ),
            MovieWithImage(
                name = "Chakram",
                imageResourceName = "movie_poster_chakram",
                variations = listOf("chakram", "chakaram"),
                releaseYear = 2005
            ),
            MovieWithImage(
                name = "Chatrapathi",
                imageResourceName = "movie_poster_chatrapathi",
                variations = listOf("chatrapathi", "chatrapati","chatraapathi","chhatrapati"),
                releaseYear = 2005
            ),
            MovieWithImage(
                name = "Coolie",
                imageResourceName = "movie_poster_coolie",
                variations = listOf("coolie", "coolie"),
                releaseYear = 2025
            ),
            MovieWithImage(
                name = "Devara",
                imageResourceName = "movie_poster_devara",
                variations = listOf("devra", "devara"),
                releaseYear = 2024
            ),
            MovieWithImage(
                name = "Dhruva",
                imageResourceName = "movie_poster_dhruva",
                variations = listOf("dhruva", "duruva"),
                releaseYear = 2016
            ),

            MovieWithImage(
                name = "Eega",
                imageResourceName = "movie_poster_eega",
                variations = listOf("eega", "ega"),
                releaseYear = 2012
            ),
            MovieWithImage(
                name = "Ghajini",
                imageResourceName = "movie_poster_gajini",
                variations = listOf("ghajini", "gajini"),
                releaseYear = 2008
            ),
            MovieWithImage(
                name = "Goodachari",
                imageResourceName = "movie_poster_goodachari",
                variations = listOf("goodachari", "goodechari","gudachari"),
                releaseYear = 2018
            ),
            MovieWithImage(
                name = "Guntur Kaaram",
                imageResourceName = "movie_poster_guntur_kaaram",
                variations = listOf("Guntur", "Kaaram","guntur karam","guntur kaaram"),
                releaseYear = 2024
            ),
            MovieWithImage(
                name = "Janatha Garage",
                imageResourceName = "movie_poster_janata_garrage",
                variations = listOf("janatha garage", "janata garage", "janata"),
                releaseYear = 2016
            ),
            MovieWithImage(
                name = "Jersey",
                imageResourceName = "movie_poster_jersey",
                variations = listOf("jersey"),
                releaseYear = 2019
            ),
            MovieWithImage(
                name = "Julayi",
                imageResourceName = "movie_poster_julayi",
                variations = listOf("julayi", "julai","july"),
                releaseYear = 2012
            ),
            MovieWithImage(
                name = "Khaleja",
                imageResourceName = "movie_poster_kaleja",
                variations = listOf("khaleja", "kaleja", "mahesh khaleja"),
                releaseYear = 2010
            ),
            MovieWithImage(
                name = "LIE",
                imageResourceName = "movie_poster_lie",
                variations = listOf("lie","ly"),
                releaseYear = 2017
            ),
            MovieWithImage(
                name = "M.S. Dhoni: The Untold Story",
                imageResourceName = "movie_poster_msdhoni",
                variations = listOf("ms dhoni", "dhoni", "untold story"),
                releaseYear = 2016
            ),

            MovieWithImage(
                name = "Mahanati",
                imageResourceName = "movie_poster_mahanati",
                variations = listOf("mahaanati", "mahanatti"),
                releaseYear = 2018
            ),
            MovieWithImage(
                name = "Manam",
                imageResourceName = "movie_poster_manam",
                variations = listOf("manam", "manum"),
                releaseYear = 2014
            ),
            MovieWithImage(
                name = "Nannaku Prematho",
                imageResourceName = "movie_poster_nanaku_prematho",
                variations = listOf("nannaku prematho", "nanaku prematho", "nanaku","nanaku prem"),
                releaseYear = 2016
            ),
            MovieWithImage(
                name = "Oopiri",
                imageResourceName = "movie_poster_oopiri",
                variations = listOf("oopiri", "ooperi", "thozha"),
                releaseYear = 2016
            ),
            MovieWithImage(
                name = "Orange",
                imageResourceName = "movie_poster_orange",
                variations = listOf("orange"),
                releaseYear = 2010
            ),
            MovieWithImage(
                name = "Pelli Choopulu",
                imageResourceName = "movie_poster_pellichupulu",
                variations = listOf("pelli choopulu", "pellichupulu"),
                releaseYear = 2016
            ),
            MovieWithImage(
                name = "Petta",
                imageResourceName = "movie_poster_petta",
                variations = listOf("petta","peta","beta"),
                releaseYear = 2019
            ),
            MovieWithImage(
                name = "Pournami",
                imageResourceName = "movie_poster_pournami",
                variations = listOf("pournami", "purnami"),
                releaseYear = 2006
            ),
            MovieWithImage(
                name = "Sye",
                imageResourceName = "movie_poster_sye",
                variations = listOf("sye", "sai", "sy","sign"),
                releaseYear = 2004
            ),
            MovieWithImage(
                name = "Tholi Prema",
                imageResourceName = "movie_poster_toliprema",
                variations = listOf("tholi prema", "toliprema", "toli prema"),
                releaseYear = 1998
            ),
            MovieWithImage(
                name = "Varsham",
                imageResourceName = "movie_poster_varsham",
                variations = listOf("varsham", "varshamm"),
                releaseYear = 2004
            )



            // TODO: Add more movies as you add poster images
        ))
    }

    fun getAllMovies(): List<MovieWithImage> {
        return movies.shuffled()
    }

    fun validateMovie(guess: String, currentMovie: MovieWithImage, usedMovies: Set<String>): Boolean {
        val normalizedGuess = guess.lowercase().trim()

        // Check if already used
        if (usedMovies.any { it.lowercase() == normalizedGuess }) {
            return false
        }

        // Check if guess matches current movie
        return currentMovie.variations.any { it == normalizedGuess } ||
                currentMovie.name.lowercase() == normalizedGuess
    }
}