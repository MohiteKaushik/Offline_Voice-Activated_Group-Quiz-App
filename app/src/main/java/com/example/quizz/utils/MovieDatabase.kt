package com.example.quizz.utils

import com.example.quizz.models.Movie

class MovieDatabase {

    private val movies = mutableMapOf<String, List<Movie>>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        // In real app, load from assets/movies_database.json
        // For now, hardcoded data

        movies["before2000"] = listOf(
            Movie("Shankarabharanam", 1980, listOf("shankarabharanam", "sankara bharanam", "shankara bharanam", "sankarabharanam")),
            Movie("Mayabazar", 1957, listOf("mayabazar", "maya bazar", "mayaa bazar", "maaya bazar")),
            Movie("Missamma", 1955, listOf("missamma", "misamma", "misama")),
            Movie("Gundamma Katha", 1962, listOf("gundamma katha", "gundama katha", "gundamma kata")),
            Movie("Muthyala Muggu", 1975, listOf("muthyala muggu", "mutyala muggu", "mutyala mugu")),
            Movie("Sagara Sangamam", 1983, listOf("sagara sangamam", "saagara sangamam", "sagara sangamam")),
            Movie("Swathi Muthyam", 1986, listOf("swathi muthyam", "swati mutyam", "swathi mutyam")),
            Movie("Geethanjali", 1989, listOf("geethanjali", "geetanjali", "geethaanjali", "gitanjali")),


        )

        // Movies After 2000 (Modern Tollywood)
        movies["after2000"] = listOf(
            Movie("Kushi", 2001, listOf("kushi", "khushi", "kusi", "kooshi", "kushi")),
            Movie("Okkadu", 2003, listOf("okkadu", "okadu", "oquadu")),
            Movie("Athadu", 2005, listOf("athadu", "atadu", "attadu", "athadoo", "athaadu")),
            Movie("Pokiri", 2006, listOf("pokiri", "pokkiri", "pokiri")),
            Movie("Magadheera", 2009, listOf("magadheera", "magadeera", "magadhira")),
            Movie("Eega", 2012, listOf("eega", "ega", "iga")),
            Movie("Attarintiki Daredi", 2013, listOf("attarintiki daredi", "attarintiki daaredi", "ad", "atharintiki daredi")),
            Movie("Race Gurram", 2014, listOf("race gurram", "racegurram", "race guram")),
            Movie("Baahubali", 2015, listOf("baahubali", "bahubali", "baahubali 1", "bahubali 1")),
            Movie("Srimanthudu", 2015, listOf("srimanthudu", "srimantudu", "srimanthadu")),
            Movie("Arjun Reddy", 2017, listOf("arjun reddy", "arjun redy", "arjun reddi")),
            Movie("Rangasthalam", 2018, listOf("rangasthalam", "ranga sthalam", "rangastalam")),
            Movie("Ala Vaikunthapurramuloo", 2020, listOf("ala vaikunthapurramuloo", "ala vaikuntapuram lo", "avpl", "ala vaikuntapuramuloo")),
            Movie("RRR", 2022, listOf("rrr", "rrrr", "triple r", "ar ar ar")),
            Movie("Pushpa", 2021, listOf("pushpa", "pushpa 1", "pushpa the rise")),
            Movie("Sarileru Neekevvaru", 2020, listOf("sarileru neekevvaru", "sarileru nikevvaru", "sarileru neekevaru")),
            Movie("Akhanda", 2021, listOf("akhanda", "akanda")),
            Movie("Gabbar Singh", 2012, listOf("gabbar singh", "gabar singh")),
            Movie("Jalsa", 2008, listOf("jalsa", "jalasa")),
            Movie("Simhadri", 2003, listOf("simhadri", "simadri", "simhadhri")),
            Movie("Dookudu", 2011, listOf("dookudu", "dokudu")),
            Movie("Khaleja", 2010, listOf("khaleja", "kaleeja")),
            Movie("Temper", 2015, listOf("temper", "tempor")),
            Movie("Mirchi", 2013, listOf("mirchi", "mirchi")),
            Movie("Kick", 2009, listOf("kick")),
            Movie("Ready", 2008, listOf("ready", "redy")),
            Movie("Yamadonga", 2007, listOf("yamadonga", "yama donga")),
            Movie("Dhee", 2007, listOf("dhee", "dee")),
            Movie("Vikramarkudu", 2006, listOf("vikramarkudu", "vikram markudu")),
            Movie("Bommarillu", 2006, listOf("bommarillu", "bomarillu"))
        )
    }

    fun getMovies(category: String): List<Movie> {
        return movies[category] ?: emptyList()
    }

    fun validateMovie(category: String, guess: String, usedMovies: Set<String>): Pair<Boolean, Movie?> {
        val movieList = getMovies(category)
        val normalizedGuess = guess.lowercase().trim()

        // Check if already used
        if (usedMovies.contains(normalizedGuess)) {
            return Pair(false, null)
        }

        // Find matching movie
        val foundMovie = movieList.find { movie ->
            movie.variations.any { it.equals(normalizedGuess, ignoreCase = true) }
        }

        return Pair(foundMovie != null, foundMovie)
    }
}