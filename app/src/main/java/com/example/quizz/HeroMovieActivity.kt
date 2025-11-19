// ============================================
// HeroMovieActivity.kt - Guess Movies of a Hero
// ============================================
package com.example.quizz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.models.Hero
import com.example.quizz.models.MicMode
import com.example.quizz.models.Team
import com.example.quizz.utils.HeroDatabase
import com.example.quizz.utils.VoiceRecognizer

class HeroMovieActivity : AppCompatActivity() {

    private lateinit var teams: List<Team>
    private var currentTeamIndex = 0
    private var winningScore = 20
    private lateinit var heroDatabase: HeroDatabase
    private var currentHero: Hero? = null
    private var usedMovies = mutableSetOf<String>()
    private lateinit var micMode: MicMode

    private lateinit var voiceRecognizer: VoiceRecognizer
    private var isWaitingForAnswer = false
    private var buttonMicActive = false

    private lateinit var tvTeam1Name: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Name: TextView
    private lateinit var tvTeam2Score: TextView
    private lateinit var tvCurrentTurn: TextView
    private lateinit var tvHeroName: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var tvUsedMovies: TextView
    private lateinit var imgHero: ImageView
    private lateinit var imgMic: ImageView
    private lateinit var btnSpeak: Button

    private val handler = Handler(Looper.getMainLooper())
    private var micCheckRunnable: Runnable? = null
    private var buttonTimerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_movie)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
        loadNewHero()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            startAlwaysOnMode()
        }
    }

    private fun initializeViews() {
        tvTeam1Name = findViewById(R.id.tvTeam1Name)
        tvTeam1Score = findViewById(R.id.tvTeam1Score)
        tvTeam2Name = findViewById(R.id.tvTeam2Name)
        tvTeam2Score = findViewById(R.id.tvTeam2Score)
        tvCurrentTurn = findViewById(R.id.tvCurrentTurn)
        tvHeroName = findViewById(R.id.tvHeroName)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvFeedback = findViewById(R.id.tvFeedback)
        tvUsedMovies = findViewById(R.id.tvUsedMovies)
        imgHero = findViewById(R.id.imgHero)
        imgMic = findViewById(R.id.imgMic)
        btnSpeak = findViewById(R.id.btnSpeak)

        btnSpeak.setOnClickListener { activateButtonMic() }
    }

    private fun setupGame() {
        val team1Name = intent.getStringExtra("team1") ?: "Team A"
        val team2Name = intent.getStringExtra("team2") ?: "Team B"
        winningScore = intent.getIntExtra("winningScore", 20)
        micMode = MicMode.valueOf(intent.getStringExtra("micMode") ?: "ALWAYS_ON_WAKEWORD")

        teams = listOf(Team(team1Name), Team(team2Name))
        heroDatabase = HeroDatabase()

        // Show/hide button based on mic mode
        if (micMode == MicMode.MANUAL_BUTTON) {
            btnSpeak.visibility = View.VISIBLE
            tvInstruction.text = "Press button to speak (3 seconds)"
        } else {
            btnSpeak.visibility = View.GONE
            tvInstruction.text = "Say 'movie [name]' anytime"
        }

        updateUI()
    }

    private fun setupVoiceRecognizer() {
        voiceRecognizer = VoiceRecognizer(
            context = this,
            onResult = { recognizedText ->
                runOnUiThread {
                    android.util.Log.d("HeroMovie", "Received: $recognizedText")

                    if (micMode == MicMode.MANUAL_BUTTON) {
                        processAnswer(recognizedText.trim())
                    } else {
                        // In always-on mode, wake word required
                        if (recognizedText.startsWith("movie ")) {
                            val heroName = recognizedText.removePrefix("movie ").trim()
                            processAnswer(heroName)
                        }
                    }
                }
            },
            onError = { error ->
                runOnUiThread {
                    android.util.Log.e("HeroMovie", "Voice error: $error")
                }
            },
            onListeningStateChange = { isListening ->
                runOnUiThread {
                    updateMicIcon(isListening)
                }
            }
        )
    }

    private fun startAlwaysOnMode() {
        isWaitingForAnswer = true
        voiceRecognizer.startListening()
        startMicMonitoring()
    }

    private fun startMicMonitoring() {
        micCheckRunnable = object : Runnable {
            override fun run() {
                if (isWaitingForAnswer && micMode == MicMode.ALWAYS_ON_WAKEWORD) {
                    ensureMicActive()
                }
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(micCheckRunnable!!, 3000)
    }

    private fun ensureMicActive() {
        voiceRecognizer.stopListening()
        handler.postDelayed({
            voiceRecognizer.startListening()
        }, 500)
    }

    private fun activateButtonMic() {
        if (buttonMicActive) return

        buttonMicActive = true
        btnSpeak.text = "LISTENING..."
        btnSpeak.isEnabled = false

        voiceRecognizer.startListening()

        // Auto-deactivate after 3 seconds
        buttonTimerRunnable = Runnable {
            deactivateButtonMic()
        }
        handler.postDelayed(buttonTimerRunnable!!, 3000)
    }

    private fun deactivateButtonMic() {
        buttonMicActive = false
        voiceRecognizer.stopListening()
        btnSpeak.text = "PRESS TO SPEAK"
        btnSpeak.isEnabled = true
        buttonTimerRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun loadNewHero() {
        currentHero = heroDatabase.getRandomHero()
        usedMovies.clear()

        currentHero?.let { hero ->
            tvHeroName.text = hero.name

            // Load hero image
            val resourceId = resources.getIdentifier(hero.imageResourceName, "drawable", packageName)
            if (resourceId != 0) {
                imgHero.setImageResource(resourceId)
            } else {
                imgHero.setImageResource(android.R.drawable.ic_menu_gallery)
                Toast.makeText(this, "Hero image not found: ${hero.imageResourceName}", Toast.LENGTH_SHORT).show()
            }
        }

        updateUsedMoviesList()
    }

    private fun processAnswer(movieName: String) {
        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()
        }

        currentHero?.let { hero ->
            val (isValid, movie) = heroDatabase.validateMovie(movieName, hero, usedMovies)

            if (isValid && movie != null) {
                // Correct answer
                teams[currentTeamIndex].score += 1
                usedMovies.add(movieName)
                usedMovies.addAll(movie.variations)

                showFeedback("✅ Correct! \"${movie.name}\"", "#4CAF50")

                // Check if all movies guessed
                if (usedMovies.size >= hero.movies.size * 2) {
                    handler.postDelayed({
                        Toast.makeText(this, "All movies guessed! New hero...", Toast.LENGTH_SHORT).show()
                        loadNewHero()
                    }, 2000)
                }

                checkWinCondition()
            } else {
                // Wrong answer
                teams[currentTeamIndex].score -= 1

                if (usedMovies.any { it.lowercase() == movieName.lowercase() }) {
                    showFeedback("❌ Already used!", "#F44336")
                } else {
                    showFeedback("❌ You said: \"$movieName\"\nNot ${hero.name}'s movie!", "#F44336")
                }
            }

            updateUI()
            updateUsedMoviesList()

            handler.postDelayed({
                tvFeedback.visibility = View.INVISIBLE
                nextTurn()
            }, 3000)
        }
    }

    private fun showFeedback(message: String, colorHex: String) {
        tvFeedback.text = message
        tvFeedback.setTextColor(android.graphics.Color.parseColor(colorHex))
        tvFeedback.visibility = View.VISIBLE
    }

    private fun updateUsedMoviesList() {
        currentHero?.let { hero ->
            val guessedNames = hero.movies.filter { movie ->
                usedMovies.any { used -> movie.variations.contains(used.lowercase()) }
            }.map { it.name }

            tvUsedMovies.text = if (guessedNames.isEmpty()) {
                "No movies guessed yet"
            } else {
                "Guessed (${guessedNames.size}/${hero.movies.size}):\n${guessedNames.joinToString(", ")}"
            }
        }
    }

    private fun nextTurn() {
        currentTeamIndex = (currentTeamIndex + 1) % teams.size
        updateUI()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            isWaitingForAnswer = true
            handler.postDelayed({
                voiceRecognizer.startListening()
            }, 1000)
        }
    }

    private fun checkWinCondition() {
        val currentTeam = teams[currentTeamIndex]
        if (currentTeam.score >= winningScore) {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()

            handler.postDelayed({
                declareWinner()
            }, 3000)
        }
    }

    private fun declareWinner() {
        val winner = teams.maxByOrNull { it.score } ?: teams[0]

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winnerName", winner.name)
        intent.putExtra("team1Name", teams[0].name)
        intent.putExtra("team1Score", teams[0].score)
        intent.putExtra("team2Name", teams[1].name)
        intent.putExtra("team2Score", teams[1].score)
        startActivity(intent)
        finish()
    }

    private fun updateUI() {
        tvTeam1Name.text = teams[0].name
        tvTeam1Score.text = teams[0].score.toString()
        tvTeam2Name.text = teams[1].name
        tvTeam2Score.text = teams[1].score.toString()

        val currentTeam = teams[currentTeamIndex]
        tvCurrentTurn.text = "${currentTeam.name}'s Turn"
    }

    private fun updateMicIcon(isListening: Boolean) {
        if (isListening) {
            imgMic.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        } else {
            imgMic.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isWaitingForAnswer = false
        voiceRecognizer.destroy()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
        buttonTimerRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onPause() {
        super.onPause()
        voiceRecognizer.stopListening()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
        if (micMode == MicMode.ALWAYS_ON_WAKEWORD && isWaitingForAnswer) {
            startMicMonitoring()
            handler.postDelayed({ voiceRecognizer.startListening() }, 500)
        }
    }
}