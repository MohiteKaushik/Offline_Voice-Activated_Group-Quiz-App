package com.example.quizz.utils

import com.example.quizz.models.Dialogue
//import com.example.quizz.models.Meme

class DialogueDatabase {

    private val dialogues = listOf(
//        Dialogue(
//            movieName = "Bhagavanth Kesari",
//            resourceName = "dialogue_bhagavanthkesari",
//            variations = listOf("bhagavanth kesari", "bhagavat kesari", "bhagavant kesar", "Bhagavanth Kesari")
//        ),
        Dialogue(
            movieName = "Bommarilu",
            resourceName = "dialogue_bommarilu",
            variations = listOf("bommarilu", "bomma rilu", "bommarill","bommarillu")
          ),
//        Dialogue(
//            movieName = "Dhruva",
//            resourceName = "dialogue_dhruva",
//            variations = listOf("Dhruva", "dhruv")
//        ),
//        Dialogue(
//            movieName = "Hit 3",
//            resourceName = "dialogue_hit3",
//            variations = listOf("Hit3", "hit")
//        ),
//        Dialogue(
//            movieName = "KGF",
//            resourceName = "dialogue_kgf",
//            variations = listOf("kgf", "kg","kgf chapter1","chapter 1")
//        ),Dialogue(
//            movieName = "Lucky Bhasker",
//            resourceName = "dialogue_lucky_bhasker",
//            variations = listOf("Lucky Bhasker", "Luckybhasker","Lucky Bhaske","luckybhasker","lucky basker")
//        ),
//        Dialogue(
//            movieName = "Mirchi",
//            resourceName = "dialogue_mirchi",
//            variations = listOf("Mirchi", "mirchi","mirch")
//        ),
//        Dialogue(
//            movieName = "Pokiri",
//            resourceName = "dialogue_pokiri",
//            variations = listOf("pokiri", "pokir")
//        ),
//
//        Dialogue(
//            movieName = "Samba",
//            resourceName = "dialogue_samba",
//            variations = listOf("Samba", "Samb")
//        ),
//        Dialogue(
//            movieName = "Vikram",
//            resourceName = "dialogue_vikram",
//            variations = listOf("Vikram", "Vikra","vikram")
//        ),

        Dialogue(
            movieName = "Adhurs",
            memeResourceName = "meme_adhurs",
            variations = listOf("Adhurs", "Athurs","aAdhur")
        ),

        Dialogue(
            movieName = "Animal",
            memeResourceName = "meme_animal",
            variations = listOf("Animal", "Animale","Aanimal")
        ),

        Dialogue(
            movieName = "Attarintiki Daredi",
            memeResourceName = "meme_attarintiki_daredi",
            variations = listOf("Attarintiki Daredi", "Atharintiki", "AD")
        ),
        Dialogue(
            movieName = "Badsha",
            memeResourceName = "meme_badsha",
            variations = listOf("Badsha", "Badshah", "Baadshah")
        ),
        Dialogue(
            movieName = "Dubai Seenu",
            memeResourceName = "meme_dubai_seenu",
            variations = listOf("Dubai Seenu", "Dubay Seenu","bubai","seenu","sinu")
        ),
        Dialogue(
            movieName = "Dookudu",
            memeResourceName = "meme_dookudu",
            variations = listOf("Dookudu", "Dukudu")
        ),
        Dialogue(
            movieName = "Ene",
            memeResourceName = "meme_ene",
            variations = listOf("Ene", "Ee", "ee nagaraniki emaindi", "nagaraniki","emaindi")
        ),
        Dialogue(
            movieName = "Guna",
            memeResourceName = "meme_guna",
            variations = listOf("Guna", "Gunaa")
        ),
        Dialogue(
            movieName = "Jai Chiranjeeva",
            memeResourceName = "meme_jai_chiranjeeva",
            variations = listOf("Jai Chiranjeeva", "Jai Chiranjeeva","jai","chiranjeeva")
        ),
        Dialogue(
            movieName = "Julayi",
            memeResourceName = "meme_julayi",
            variations = listOf("Julayi", "Julai","july")
        ),
        Dialogue(
            movieName = "King",
            memeResourceName = "meme_king",
            variations = listOf("King", "Kingu")
        ),
        Dialogue(
            movieName = "Laxmi",
            memeResourceName = "meme_laxmi",
            variations = listOf("Laxmi", "Lakshmi","laxmy")
        ),
        Dialogue(
            movieName = "Legend",
            memeResourceName = "meme_legend",
            variations = listOf("Legend", "Legende")
        ),
        Dialogue(
            movieName = "Manmadudu",
            memeResourceName = "meme_manmadudu",
            variations = listOf("Manmadudu", "Manmadhudu")
        ),
        Dialogue(
            movieName = "Mirchi",
            memeResourceName = "meme_mirchi",
            variations = listOf("Mirchi", "Mirchie")
        ),
        Dialogue(
            movieName = "Mr Perfect",
            memeResourceName = "meme_me_perfect",
            variations = listOf("Mr Perfect", "Mr. Perfect", "Perfect","mister perfect","mister")
        ),
        Dialogue(
            movieName = "Naayak",
            memeResourceName = "meme_naayak",
            variations = listOf("Naayak", "Nayak")
        ),
        Dialogue(
            movieName = "Neninte",
            memeResourceName = "meme_neninte",
            variations = listOf("Neninte", "Nenanthe")
        ),
        Dialogue(
            movieName = "Nuvvu Naaku Nachav",
            memeResourceName = "meme_nuvu_naaku_nachav",
            variations = listOf("Nuvvu Naaku Nachav", "Nuvu Naku Nachav", "Nuvvu Naaku Nachaav","nuvu naaku")
        ),
        Dialogue(
            movieName = "Nuvvu Nenu",
            memeResourceName = "meme_nuvu_nenu",
            variations = listOf("Nuvvu Nenu", "Nuvu Nenu", "Nuvvu Nuv")
        ),
        Dialogue(
            movieName = "Pellichupulu",
            memeResourceName = "meme_pellichupulu",
            variations = listOf("Pellichupulu", "Pelli Choopulu")
        ),
        Dialogue(
            movieName = "Pokiri",
            memeResourceName = "meme_pokiri",
            variations = listOf("Pokiri", "Porkiri")
        ),
        Dialogue(
            movieName = "Prathiroju Pandage",
            memeResourceName = "meme_prathiroju_pandage",
            variations = listOf("Prathiroju Pandage", "Prathi Roju Pandage", "PRP")
        ),
        Dialogue(
            movieName = "Race Gurram",
            memeResourceName = "meme_race_gurram",
            variations = listOf("Race Gurram", "Rase Gurram")
        ),
        Dialogue(
            movieName = "Ready",
            memeResourceName = "meme_ready",
            variations = listOf("Ready", "Reddy")
        ),
        Dialogue(
            movieName = "Sontham",
            memeResourceName = "meme_sontham",
            variations = listOf("Sontham", "Sontam")
        ),
        Dialogue(
            movieName = "S/O Satyamurthy",
            memeResourceName = "meme_sos",
            variations = listOf("S/O Satyamurthy", "S O Satyamurthy", "SOS")
        ),
        Dialogue(
            movieName = "SVSC",
            memeResourceName = "meme_svsc",
            variations = listOf("SVSC", "Seethamma Vakitlo Sirimalle Chettu","Seethamma Vakitlo")
        ),
        Dialogue(
            movieName = "Tulsi",
            memeResourceName = "meme_tulsi",
            variations = listOf("Tulsi", "Tulse")
        ),
        Dialogue(
            movieName = "Veerabadra",
            memeResourceName = "meme_veerabadra",
            variations = listOf("Veerabadra", "Veerabhadra")
        ),
        Dialogue(
            movieName = "Venki",
            memeResourceName = "meme_venki",
            variations = listOf("Venki", "Venky")
        ),
        Dialogue(
            movieName = "Vikramarkudu",
            memeResourceName = "meme_vikramarkudu",
            variations = listOf("Vikramarkudu", "Vikramakudu")
        ),
        Dialogue(
            movieName = "Yevadu",
            memeResourceName = "meme_yevadu",
            variations = listOf("Yevadu", "Yevvad", "Evadu")
        ),
        Dialogue(
            movieName = "Yuganiki Okaadu",
            memeResourceName = "meme_yuganiki_okaadu",
            variations = listOf("Yuganiki Okaadu", "Yuganiki Okkadu", "YO","Yuganiki")
        )



        // Add more dialogues here
    )

    fun getAllDialogues(): List<Dialogue> = dialogues.shuffled()

    fun validateDialogue(
        userAnswer: String,
        correctDialogue: Dialogue,
        usedDialogues: Set<String>
    ): Pair<Boolean, Dialogue?> {
        val normalizedAnswer = userAnswer.lowercase().trim()

        if (usedDialogues.any { it.lowercase() == normalizedAnswer }) {
            return Pair(false, null)
        }

        val isCorrect = normalizedAnswer == correctDialogue.movieName.lowercase() ||
                correctDialogue.variations.any { it.lowercase() == normalizedAnswer }

        return Pair(isCorrect, if (isCorrect) correctDialogue else null)
    }
}