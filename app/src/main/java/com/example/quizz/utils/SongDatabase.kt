package com.example.quizz.utils

import com.example.quizz.models.Song

class SongDatabase {

    private val songs = mutableListOf<Song>()

    init {
        loadSongs()
    }

    private fun loadSongs() {
        // Add your songs here
        // Make sure the resourceName matches your file in res/raw/

        songs.addAll(listOf(
            // After 2000 movies
            Song(
                movieName = "Animal",
                resourceName = "song_animal",
                variations = listOf("Animal", "animaal", "annimal")
            ),
            Song(
                movieName = "Arjun Reddy",
                resourceName = "song_arjun_reddy",
                variations = listOf("arjun reddy", "arjun redy", "arjun reddi")
            ),
            Song(
                movieName = "Athadu",
                resourceName = "song_athadu",
                variations = listOf("athadu", "atadu", "attadu", "athadoo")
            ),
            Song(
                movieName = "Ala Vaikunthapurramuloo",
                resourceName = "song_avpl",
                variations = listOf("ala vaikunthapurramuloo", "ala vaikuntapuram lo", "avpl")
            ),
            Song(
                movieName = "Awara",
                resourceName = "song_awara",
                variations = listOf("awara", "aawara", "avara")
            ),

            Song(
                movieName = "Baahubali",
                resourceName = "song_baahubali",
                variations = listOf("baahubali", "bahubali", "baahubali 1")
            ),
            Song(
                movieName = "billa",
                resourceName = "song_billa",
                variations = listOf("billa", "bila", "bela")
            ),
            Song(
                movieName = "Daaku_Maharaaj",
                resourceName = "song_daaku_maharaaj",
                variations = listOf("Daaku_Maharaaj", "Daaku", "Maharaj","Daku Maharaaj","Daku Maharaj","daaku maharaaj","daku maharaj")
            ),
            Song(
                movieName = "Eega",
                resourceName = "song_eega",
                variations = listOf("eega", "ega", "iga")
            ),
            Song(
                movieName = "Gharshana",
                resourceName = "song_gharshana",
                variations = listOf("Gharshana", "Gharshanaa", "Garshana","gharshana")
            ),
            Song(
                movieName = "GunturKaaram",
                resourceName = "song_guntur_kaaram",
                variations = listOf("GunturKaaram", "GunturKaram", "GunturrKaaram","Guntur Kaaram","guntur karam")
            ),
            Song(
                movieName = "Kalki",
                resourceName = "song_kalki",
                variations = listOf("Kalki", "Kalkki", "Kaki")
            ),
            Song(
                movieName = "Kantara",
                resourceName = "song_kantara",
                variations = listOf("Kantara", "kantar", "Kantaraa")
            ),
            Song(
                movieName = "Kingdom",
                resourceName = "song_kingdom",
                variations = listOf("Kingdom", "Kingddom", "Kingdomm")
            ),
            Song(
                movieName = "Kubera",
                resourceName = "song_kubera",
                variations = listOf("Kubera", "Kuberaa", "Kubbera")
            ),
            Song(
                movieName = "Leo",
                resourceName = "song_leo",
                variations = listOf("Leo", "leoo", "Lleo")
            ),
            Song(
                movieName = "Magadheera",
                resourceName = "song_magadheera",
                variations = listOf("magadheera", "magadeera", "magadhira")
            ),
            Song(
                movieName = "Manaadu",
                resourceName = "song_manaadu",
                variations = listOf("Manaadu", "Manadu", "Loop","The loop")
            ),
            Song(
                movieName = "Marco",
                resourceName = "song_marco",
                variations = listOf("Marco", "Marcco", "Marcoo","marrco")
            ),
            Song(
                movieName = "Master",
                resourceName = "song_master",
                variations = listOf("Master", "masterr", "Mastter", "Masterr")
            ),
            Song(
                movieName = "Oye",
                resourceName = "song_oye",
                variations = listOf("Oye", "Oyye", "oy","oa")
            ),
            Song(
                movieName = "Pokiri",
                resourceName = "song_pokiri",
                variations = listOf("pokiri", "pokkiri")
            ),
            Song(
                movieName = "Pushpa",
                resourceName = "song_pushpa",
                variations = listOf("pushpa", "pushpa 1", "pushpa the rise")
            ),

            Song(
                movieName = "Rangasthalam",
                resourceName = "song_rangasthalam",
                variations = listOf("rangasthalam", "ranga sthalam", "rangastalam","rangastala","rangastha")
            ),
            Song(
                movieName = "RRR",
                resourceName = "song_rrr",
                variations = listOf("rrr", "rrrr", "triple r", "ar ar ar","R R", "are are","are are are","rr","R r")
            ),
            Song(
                movieName = "Saripodaa_sanivaram",
                resourceName = "song_saripodaa_sanivaram",
                variations = listOf("Saripodaa_sanivaram", "Saripodaa", "Saripodaa sanivaram")
            ),
            Song(
                movieName = "Pushpa",
                resourceName = "song_pushpa",
                variations = listOf("pushpa", "pushpa 1", "pushpa the rise")
            ),
            Song(
                movieName = "Vikram",
                resourceName = "song_vikram",
                variations = listOf("Vikram", "vikrram", "Vikramm")
            ),




            // Before 2000 movies
//            Song(
//                movieName = "Shankarabharanam",
//                resourceName = "song_shankarabharanam",
//                variations = listOf("shankarabharanam", "sankara bharanam", "shankara bharanam")
//            ),
//            Song(
//                movieName = "Geethanjali",
//                resourceName = "song_geethanjali",
//                variations = listOf("geethanjali", "geetanjali", "geethaanjali", "gitanjali")
//            ),
//            Song(
//                movieName = "Sagara Sangamam",
//                resourceName = "song_sagara_sangamam",
//                variations = listOf("sagara sangamam", "saagara sangamam")
//            ),
//            Song(
//                movieName = "Swathi Muthyam",
//                resourceName = "song_swathi_muthyam",
//                variations = listOf("swathi muthyam", "swati mutyam", "swathi mutyam")
//            )

            // TODO: Add more songs as you add audio files to res/raw/
        ))
    }

    fun getAllSongs(): List<Song> {
        return songs.shuffled()  // Return in random order for variety
    }

    fun validateSong(guess: String, currentSong: Song, usedSongs: Set<String>): Pair<Boolean, Song?> {
        val normalizedGuess = guess.lowercase().trim()

        // Check if already used
        if (usedSongs.any { it.lowercase() == normalizedGuess }) {
            return Pair(false, null)
        }

        // Check if guess matches current song
        val isMatch = currentSong.variations.any { it == normalizedGuess } ||
                currentSong.movieName.lowercase() == normalizedGuess

        return Pair(isMatch, if (isMatch) currentSong else null)
    }

    fun getSongCount(): Int {
        return songs.size
    }
}