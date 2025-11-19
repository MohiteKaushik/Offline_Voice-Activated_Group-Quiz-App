// ============================================
// ImageGuessActivity.kt - Guess Movie from Poster
// ============================================
package com.example.quizz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.models.MicMode
import com.example.quizz.models.MovieWithImage
import com.example.quizz.models.Team
import com.example.quizz.utils.MovieImageDatabase
import com.example.quizz.utils.VoiceRecognizer

class ImageGuessActivity : AppCompatActivity() {

    private lateinit var teams: List<Team>
    private var currentTeamIndex = 0
    private var winningScore = 20
    private lateinit var movieDatabase: MovieImageDatabase
    private var movies = mutableListOf<MovieWithImage>()
    private var currentMovieIndex = 0
    private var usedMovies = mutableSetOf<String>()
    private lateinit var micMode: MicMode



    private lateinit var voiceRecognizer: VoiceRecognizer
    private var isWaitingForAnswer = false
    private var buttonMicActive = false
    private var timeRemaining = 20
    private var timerRunning = false

    private lateinit var tvTeam1Name: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Name: TextView
    private lateinit var tvTeam2Score: TextView
    private lateinit var tvCurrentTurn: TextView
    private lateinit var tvTimer: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var imgMoviePoster: ImageView
    private lateinit var imgMic: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSpeak: Button

    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var micCheckRunnable: Runnable? = null
    private var buttonTimerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_guess)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
        loadNextMovie()

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
        tvTimer = findViewById(R.id.tvTimer)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvFeedback = findViewById(R.id.tvFeedback)
        imgMoviePoster = findViewById(R.id.imgMoviePoster)
        imgMic = findViewById(R.id.imgMic)
        progressBar = findViewById(R.id.progressBar)
        btnSpeak = findViewById(R.id.btnSpeak)

        btnSpeak.setOnClickListener { activateButtonMic() }
    }

    private fun setupGame() {
        val team1Name = intent.getStringExtra("team1") ?: "Team A"
        val team2Name = intent.getStringExtra("team2") ?: "Team B"
        winningScore = intent.getIntExtra("winningScore", 20)
        micMode = MicMode.valueOf(intent.getStringExtra("micMode") ?: "ALWAYS_ON_WAKEWORD")

        teams = listOf(Team(team1Name), Team(team2Name))
        movieDatabase = MovieImageDatabase()
        movies = movieDatabase.getAllMovies().toMutableList()

        if (movies.isEmpty()) {
            Toast.makeText(this, "No movie posters available!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (micMode == MicMode.MANUAL_BUTTON) {
            btnSpeak.visibility = View.VISIBLE
            tvInstruction.text = "Press button to speak"
        } else {
            btnSpeak.visibility = View.GONE
            tvInstruction.text = "Say 'movie [name]' within 20 seconds"
        }

        updateUI()
    }

    private fun setupVoiceRecognizer() {
        voiceRecognizer = VoiceRecognizer(
            context = this,
            onResult = { recognizedText ->
                runOnUiThread {
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
                    android.util.Log.e("ImageGuess", "Voice error: $error")
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
                if (isWaitingForAnswer && micMode == MicMode.ALWAYS_ON_WAKEWORD && timerRunning) {
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
        if (buttonMicActive || !timerRunning) return

        buttonMicActive = true
        btnSpeak.text = "LISTENING..."
        btnSpeak.isEnabled = false

        voiceRecognizer.startListening()

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

    private fun loadNextMovie() {
        if (currentMovieIndex >= movies.size) {
            Toast.makeText(this, "All posters completed!", Toast.LENGTH_SHORT).show()
            declareWinner()
            return
        }

        val currentMovie = movies[currentMovieIndex]

        // Load movie poster
        val resourceId = resources.getIdentifier(currentMovie.imageResourceName, "drawable", packageName)
        if (resourceId != 0) {
            imgMoviePoster.setImageResource(resourceId)
        } else {
            imgMoviePoster.setImageResource(android.R.drawable.ic_menu_gallery)
            Toast.makeText(this, "Poster not found: ${currentMovie.imageResourceName}", Toast.LENGTH_SHORT).show()
        }

        startTimer()
    }

    private fun startTimer() {
        timeRemaining = 20
        timerRunning = true
        progressBar.max = 20
        updateTimerDisplay()

        timerRunnable = object : Runnable {
            override fun run() {
                if (timerRunning && timeRemaining > 0) {
                    timeRemaining--
                    updateTimerDisplay()
                    progressBar.progress = 20 - timeRemaining
                    handler.postDelayed(this, 1000)
                } else if (timeRemaining <= 0) {
                    onTimerExpired()
                }
            }
        }
        handler.post(timerRunnable!!)
    }

    private fun stopTimer() {
        timerRunning = false
        timerRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun updateTimerDisplay() {
        tvTimer.text = "⏱️ $timeRemaining sec"

        // Change color based on time
        val color = when {
            timeRemaining > 10 -> "#4CAF50"
            timeRemaining > 5 -> "#FF9800"
            else -> "#F44336"
        }
        tvTimer.setTextColor(android.graphics.Color.parseColor(color))
    }

    private fun onTimerExpired() {
        stopTimer()

        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            voiceRecognizer.stopListening()
        }

        val currentMovie = movies[currentMovieIndex]
        showFeedback("⏱️ Time's up!\nCorrect Answer: \"${currentMovie.name}\"", "#FF9800")

        // No score change for timeout

        handler.postDelayed({
            tvFeedback.visibility = View.INVISIBLE
            nextTurn()
        }, 4000)
    }

    private fun processAnswer(movieName: String) {
        if (!timerRunning) return

        stopTimer()

        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()
        }

        val currentMovie = movies[currentMovieIndex]
        val isCorrect = movieDatabase.validateMovie(movieName, currentMovie, usedMovies)

        if (isCorrect) {
            teams[currentTeamIndex].score += 1
            usedMovies.add(movieName)
            usedMovies.addAll(currentMovie.variations)

            showFeedback("✅ Correct! \"${currentMovie.name}\"", "#4CAF50")
            checkWinCondition()
        } else {
            teams[currentTeamIndex].score -= 1
            showFeedback("❌ You said: \"$movieName\"\nCorrect: \"${currentMovie.name}\"", "#F44336")


//            if (usedMovies.any { it.lowercase() == movieName.lowercase() }) {
//                showFeedback("❌ Already used!", "#F44336")
//            } else {
//                showFeedback("❌ You said: \"$movieName\"\nCorrect: \"${currentMovie.name}\"", "#F44336")
//            }
        }

        updateUI()

        handler.postDelayed({
            tvFeedback.visibility = View.INVISIBLE
            nextTurn()
        }, 4000)
    }

    private fun showFeedback(message: String, colorHex: String) {
        tvFeedback.text = message
        tvFeedback.setTextColor(android.graphics.Color.parseColor(colorHex))
        tvFeedback.visibility = View.VISIBLE
    }

    private fun nextTurn() {
        currentTeamIndex = (currentTeamIndex + 1) % teams.size
        currentMovieIndex++

        updateUI()

        handler.postDelayed({
            loadNextMovie()

            if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
                isWaitingForAnswer = true
                voiceRecognizer.startListening()
            }
        }, 1000)
    }

    private fun checkWinCondition() {
        val currentTeam = teams[currentTeamIndex]
        if (currentTeam.score >= winningScore) {
            isWaitingForAnswer = false
            stopTimer()
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
        stopTimer()
        voiceRecognizer.destroy()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
        buttonTimerRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
        voiceRecognizer.stopListening()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onResume() {
        super.onResume()
        if (micMode == MicMode.ALWAYS_ON_WAKEWORD && isWaitingForAnswer && timerRunning) {
            startMicMonitoring()
            handler.postDelayed({ voiceRecognizer.startListening() }, 500)
        }
    }
}