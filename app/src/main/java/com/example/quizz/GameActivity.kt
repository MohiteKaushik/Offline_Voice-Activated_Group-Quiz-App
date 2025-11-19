// ============================================
// GameActivity.kt (COMPLETE - NO ERRORS)
// ============================================
package com.example.quizz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.models.GameState
import com.example.quizz.models.Team
import com.example.quizz.utils.MovieDatabase
import com.example.quizz.utils.VoiceRecognizer

class GameActivity : AppCompatActivity() {

    private lateinit var gameState: GameState
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var voiceRecognizer: VoiceRecognizer
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var tvTeam1Name: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Name: TextView
    private lateinit var tvTeam2Score: TextView
    private lateinit var tvCurrentTurn: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var imgMic: ImageView
    private lateinit var tvTapHint: TextView

    private var isWaitingForAnswer = true
    private var micCheckRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
        setupTapToActivate()

        // Start listening after a short delay
        handler.postDelayed({ startListening() }, 1000)
        startMicMonitoring()
    }
    private fun setupTapToActivate() {
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Quick tap feedback
                tvTapHint.alpha = 0.5f
                tvTapHint.animate().alpha(1f).setDuration(300).start()

                // Force restart mic if not listening
                ensureMicActive()
            }
            false  // Don't consume the event
        }
    }
    // NEW: Periodic mic health check
    private fun startMicMonitoring() {
        micCheckRunnable = object : Runnable {
            override fun run() {
                ensureMicActive()
                handler.postDelayed(this, 3000)  // Check every 3 seconds
            }
        }
        handler.postDelayed(micCheckRunnable!!, 3000)
    }

    private fun ensureMicActive() {
        // Check if mic should be active but isn't
        if (isWaitingForAnswer) {
            // Force restart if not already listening
            android.util.Log.d("SongGame", "Ensuring mic is active...")
            voiceRecognizer.stopListening()
            handler.postDelayed({
                voiceRecognizer.startListening()
            }, 500)
        }
    }

    private fun initializeViews() {
        tvTeam1Name = findViewById(R.id.tvTeam1Name)
        tvTeam1Score = findViewById(R.id.tvTeam1Score)
        tvTeam2Name = findViewById(R.id.tvTeam2Name)
        tvTeam2Score = findViewById(R.id.tvTeam2Score)
        tvCurrentTurn = findViewById(R.id.tvCurrentTurn)
        tvCategory = findViewById(R.id.tvCategory)
        tvFeedback = findViewById(R.id.tvFeedback)
        tvInstruction = findViewById(R.id.tvInstruction)
        imgMic = findViewById(R.id.imgMic)
    }

    private fun setupGame() {
        val team1Name = intent.getStringExtra("team1") ?: "Team A"
        val team2Name = intent.getStringExtra("team2") ?: "Team B"
        val winningScore = intent.getIntExtra("winningScore", 20)
        val category = intent.getStringExtra("category") ?: "before2000"


        val teams = listOf(Team(team1Name), Team(team2Name))
        gameState = GameState(teams, 0, category, mutableSetOf(), winningScore)

        // FIXED: No parameter needed
        movieDatabase = MovieDatabase()

        updateUI()
    }

    private fun setupVoiceRecognizer() {
        // FIXED: Added onListeningStateChange parameter
        voiceRecognizer = VoiceRecognizer(
            context = this,
            onResult = { recognizedText ->
                runOnUiThread {
                    // recognizedText is the FULL text like "movie athadu"
                    android.util.Log.d("GameActivity", "Received: $recognizedText")

                    // Extract movie name
                    if (recognizedText.startsWith("movie ")) {
                        val movieName = recognizedText.removePrefix("movie ").trim()
                        android.util.Log.d("GameActivity", "Extracted movie: $movieName")
                        processGuess(movieName)
                    }
                }
            },
            onError = { error ->
                runOnUiThread {
                    // Only show critical errors
                    if (error.contains("permission", ignoreCase = true)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            onListeningStateChange = { isListening ->
                runOnUiThread {
                    updateMicIcon(isListening)
                }
            }
        )
    }

    private fun startListening() {
        voiceRecognizer.startListening()
    }

    private fun updateMicIcon(isListening: Boolean) {
        if (isListening) {
            imgMic.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        } else {
            imgMic.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    private fun processGuess(movieName: String) {
        // Stop listening while processing guess
        voiceRecognizer.stopListening()

        val (isValid, movie) = movieDatabase.validateMovie(
            gameState.gameMode,
            movieName,
            gameState.usedMovies
        )

        if (isValid && movie != null) {
            // Correct answer
            gameState.teams[gameState.currentTeamIndex].score += 1
            gameState.usedMovies.add(movieName)
            gameState.usedMovies.addAll(movie.variations)

            tvFeedback.text = "✅ Correct! \"${movie.name}\""
            tvFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
            tvFeedback.visibility = View.VISIBLE

            checkWinCondition()
        } else {
            // Wrong answer
            gameState.teams[gameState.currentTeamIndex].score -= 1

            tvFeedback.text = if (gameState.usedMovies.any { it.lowercase() == movieName.lowercase() }) {
                "❌ Already used!"
            } else {
                "❌ Wrong movie!"
            }
            tvFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            tvFeedback.visibility = View.VISIBLE
        }

        updateUI()

        // Next turn after delay, then restart listening
        handler.postDelayed({
            tvFeedback.visibility = View.INVISIBLE
            nextTurn()
            // Restart listening for next team
            handler.postDelayed({
                startListening()
            }, 500)
        }, 3000)  // 3 seconds - time to see result
    }

    private fun nextTurn() {
        gameState.currentTeamIndex = (gameState.currentTeamIndex + 1) % gameState.teams.size
        updateUI()
    }

    private fun checkWinCondition() {
        val currentTeam = gameState.teams[gameState.currentTeamIndex]
        if (currentTeam.score >= gameState.winningScore) {
            voiceRecognizer.stopListening()

            handler.postDelayed({
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("winnerName", currentTeam.name)
                intent.putExtra("team1Name", gameState.teams[0].name)
                intent.putExtra("team1Score", gameState.teams[0].score)
                intent.putExtra("team2Name", gameState.teams[1].name)
                intent.putExtra("team2Score", gameState.teams[1].score)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun updateUI() {
        tvTeam1Name.text = gameState.teams[0].name
        tvTeam1Score.text = gameState.teams[0].score.toString()
        tvTeam2Name.text = gameState.teams[1].name
        tvTeam2Score.text = gameState.teams[1].score.toString()

        val currentTeam = gameState.teams[gameState.currentTeamIndex]
        tvCurrentTurn.text = "${currentTeam.name}'s Turn"

        tvCategory.text = if (gameState.gameMode == "before2000") {
            "Movies Before 2000"
        } else {
            "Movies After 2000"
        }

        tvInstruction.text = "Say: \"movie [movie name]\""
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceRecognizer.destroy()
    }

    override fun onPause() {
        super.onPause()
        voiceRecognizer.stopListening()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
       // startMicMonitoring()
        if (isWaitingForAnswer) {
            handler.postDelayed({ startListening() }, 500)
        }
    }
}