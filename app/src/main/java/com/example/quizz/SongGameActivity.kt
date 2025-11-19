// ============================================
// SongGameActivity.kt - Guess Movie by Song
// ============================================
package com.example.quizz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.models.MicMode
import com.example.quizz.models.Song
import com.example.quizz.models.Team
import com.example.quizz.utils.SongDatabase
import com.example.quizz.utils.VoiceRecognizer

class SongGameActivity : AppCompatActivity() {

    // Game State
    private lateinit var teams: List<Team>
    private var currentTeamIndex = 0
    private var winningScore = 20
    private lateinit var songDatabase: SongDatabase
    private var songs = mutableListOf<Song>()
    private var currentSongIndex = 0
    private var usedSongs = mutableSetOf<String>()

    // Audio
    private var mediaPlayer: MediaPlayer? = null
    private var winnerMediaPlayer: MediaPlayer? = null
    private var isSongPlaying = false
    private var songVolume = 0.3f  // Lower volume so voice is heard
    private lateinit var micMode: MicMode

    // Voice Recognition
    private lateinit var voiceRecognizer: VoiceRecognizer
    private var isWaitingForAnswer = false
    private var micRestartAttempts = 0

    private var buttonMicActive = false

    // Auto-skip countdown
    private var autoSkipRunnable: Runnable? = null
    private var countdownSeconds = 3

    // UI Elements
    private lateinit var tvTeam1Name: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Name: TextView
    private lateinit var tvTeam2Score: TextView
    private lateinit var tvCurrentTurn: TextView
    private lateinit var tvSongStatus: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvFeedback: TextView
   // private lateinit var btnRepeat: Button
   // private lateinit var btnSkip: Button
    private lateinit var imgMic: ImageView
    private lateinit var btnSpeak: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTapHint: TextView
    private lateinit var tvCountdown: TextView

    private val handler = Handler(Looper.getMainLooper())
    private var progressUpdateRunnable: Runnable? = null
    private var micCheckRunnable: Runnable? = null
    private var buttonTimerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_game)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
       // setupTapToActivate()
        startMicMonitoring()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            startAlwaysOnMode()
        }

        loadAndPlayFirstSong()
    }

    private fun initializeViews() {
        tvTeam1Name = findViewById(R.id.tvTeam1Name)
        tvTeam1Score = findViewById(R.id.tvTeam1Score)
        tvTeam2Name = findViewById(R.id.tvTeam2Name)
        tvTeam2Score = findViewById(R.id.tvTeam2Score)
        tvCurrentTurn = findViewById(R.id.tvCurrentTurn)
        tvSongStatus = findViewById(R.id.tvSongStatus)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvFeedback = findViewById(R.id.tvFeedback)
       // btnRepeat = findViewById(R.id.btnRepeat)
       // btnSkip = findViewById(R.id.btnSkip)
        imgMic = findViewById(R.id.imgMic)
        progressBar = findViewById(R.id.progressBar)
        tvTapHint = findViewById(R.id.tvTapHint)
        btnSpeak = findViewById(R.id.btnSpeak)
        tvCountdown = findViewById(R.id.tvCountdown)

       // btnRepeat.setOnClickListener { repeatSong() }
       // btnSkip.setOnClickListener { skipSong() }


        btnSpeak.setOnClickListener { activateButtonMic() }
    }

    private fun setupGame() {
        val team1Name = intent.getStringExtra("team1") ?: "Team A"
        val team2Name = intent.getStringExtra("team2") ?: "Team B"
        winningScore = intent.getIntExtra("winningScore", 20)
        micMode = MicMode.valueOf(intent.getStringExtra("micMode") ?: "ALWAYS_ON_WAKEWORD")

        teams = listOf(Team(team1Name), Team(team2Name))

        songDatabase = SongDatabase()
        songs = songDatabase.getAllSongs().toMutableList()

        if (songs.isEmpty()) {
            Toast.makeText(this, "No songs available! Add songs to res/raw/", Toast.LENGTH_LONG).show()
            finish()
            return
        }
         //Show/hide button based on mic mode
        if (micMode == MicMode.MANUAL_BUTTON) {
            btnSpeak.visibility = View.VISIBLE
          //  btnRepeat.visibility = View.GONE  // Hide initially
         //   btnSkip.visibility = View.GONE    // Hide initially
            tvTapHint.visibility = View.GONE
            tvInstruction.text = "Press button to speak (3 seconds)"
        } else {
            btnSpeak.visibility = View.GONE
         //   btnRepeat.visibility = View.VISIBLE
          //  btnSkip.visibility = View.VISIBLE
            tvInstruction.text = "Say  'stop' or 'movie [name]' to answer"
        }
        tvCountdown.visibility = View.GONE

        updateUI()
    }
    private fun startAlwaysOnMode() {
        isWaitingForAnswer = true
        voiceRecognizer.startListening()
        startMicMonitoring()
    }

    private fun setupVoiceRecognizer() {
        voiceRecognizer = VoiceRecognizer(
            context = this,
            onResult = { recognizedText ->
                runOnUiThread {
                    micRestartAttempts = 0  // Reset counter on successful recognition
                    android.util.Log.d("SongGame", "Received from VoiceRecognizer: $recognizedText")
                    processVoiceCommand(recognizedText)
                }
            },
            onError = { error ->
                runOnUiThread {
                    android.util.Log.e("SongGame", "Voice error: $error")
//                    if (error.contains("permission", ignoreCase = true)) {
//                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                    }
                }
            },
            onListeningStateChange = { isListening ->
                runOnUiThread {
                    updateMicIcon(isListening)
                }
            }
        )
    }
    // NEW: Tap anywhere to reactivate mic if stuck
//    private fun setupTapToActivate() {
//        val rootView = findViewById<View>(android.R.id.content)
//        rootView.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                // Quick tap feedback
//                tvTapHint.alpha = 0.5f
//                tvTapHint.animate().alpha(1f).setDuration(300).start()
//
//                // Force restart mic if not listening
//                ensureMicActive()
//            }
//            false  // Don't consume the event
//        }
//    }
    // NEW: Periodic mic health check
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
    private fun activateButtonMic() {
        // Pause song when button pressed
        if (isSongPlaying) {
            mediaPlayer?.pause()
            isSongPlaying = false
            stopProgressBar()
            tvSongStatus.text = "⏸️ Listening..."
        }


        if (buttonMicActive) return

        buttonMicActive = true
        btnSpeak.text = "LISTENING..."
        btnSpeak.isEnabled = false

        voiceRecognizer.startListening()

        // Auto-deactivate after 3 seconds
        buttonTimerRunnable = Runnable {
            if (buttonMicActive) {
                deactivateButtonMic()
                Toast.makeText(this, "No answer detected. Try again.", Toast.LENGTH_SHORT).show()
                // Resume song if it was playing
                if (mediaPlayer != null && !isSongPlaying) {
                    mediaPlayer?.start()
                    isSongPlaying = true
                    startProgressBar()
                    tvSongStatus.text = "🎵 Song Playing..."
                    btnSpeak.isEnabled = true
                }
            }
        }
        handler.postDelayed(buttonTimerRunnable!!, 5000)
    }
    private fun deactivateButtonMic() {
        buttonMicActive = false
        voiceRecognizer.stopListening()
        btnSpeak.text = "PRESS TO SPEAK"
        btnSpeak.isEnabled = true
        buttonTimerRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun loadAndPlayFirstSong() {
        if (currentSongIndex >= songs.size) {
            // All songs used
            Toast.makeText(this, "All songs completed!", Toast.LENGTH_SHORT).show()
            //stopSong()
            declareWinner()
            return
        }

        val currentSong = songs[currentSongIndex]
        playSong(currentSong)
        // Only start voice recognition if not already started
        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            startVoiceRecognition()
        }
    }

    private fun playSong(song: Song) {
        try {
            // Release previous player
            releaseMediaPlayer()

            // Get resource ID
            val resourceId = resources.getIdentifier(song.resourceName, "raw", packageName)

            if (resourceId == 0) {
                Toast.makeText(this, "Song file not found: ${song.resourceName}.mp3", Toast.LENGTH_LONG).show()
               // skipSong()
                autoSkipToNextSong()
                return
            }

            // Create and start media player
            mediaPlayer = MediaPlayer.create(this, resourceId)
            // IMPORTANT: Lower volume so voice recognition works
            mediaPlayer?.setVolume(songVolume, songVolume)

            mediaPlayer?.setOnCompletionListener {
                onSongComplete()
            }

            mediaPlayer?.start()
            isSongPlaying = true

            tvSongStatus.text = "🎵 Song Playing..."
            // Update instruction based on mode
            if (micMode == MicMode.MANUAL_BUTTON) {
                tvInstruction.text = "Press button to say answer"
                btnSpeak.isEnabled = true
              //  btnRepeat.visibility = View.GONE
               // btnSkip.visibility = View.GONE
            } else {
                tvInstruction.text = "Say 'stop' OR say 'movie [name]' anytime"
               // btnRepeat.isEnabled = false
              //  btnSkip.isEnabled = false
            }

            // Start progress bar
            startProgressBar()

        } catch (e: Exception) {
            Toast.makeText(this, "Error playing song: ${e.message}", Toast.LENGTH_SHORT).show()
            //skipSong()
            autoSkipToNextSong()
        }
    }

    private fun startProgressBar() {
        progressBar.max = mediaPlayer?.duration ?: 30000
        progressBar.progress = 0

        progressUpdateRunnable = object : Runnable {
            override fun run() {
                if (mediaPlayer != null && isSongPlaying) {
                    progressBar.progress = mediaPlayer?.currentPosition ?: 0
                    handler.postDelayed(this, 100)
                }
            }
        }
        handler.post(progressUpdateRunnable!!)
    }

    private fun stopProgressBar() {
        progressUpdateRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun onSongComplete() {
        isSongPlaying = false
        stopProgressBar()
        progressBar.progress = progressBar.max

        tvSongStatus.text = "🔇 Song Ended"

        // Show the correct answer in the middle
        val currentSong = songs[currentSongIndex]
        showFeedback("🎬 Movie: ${currentSong.movieName}", "#FFD700")


        if (micMode == MicMode.MANUAL_BUTTON) {
           // tvInstruction.text = "Press button or wait for auto-skip"
            tvInstruction.text = "Time's up! Moving to next song..."
            btnSpeak.isEnabled = false
           // btnRepeat.visibility = View.VISIBLE
           // btnSkip.visibility = View.VISIBLE
        } else {
            tvInstruction.text = "Time's up! Moving to next song..."
        }
      //  btnRepeat.isEnabled = true
        //btnSkip.isEnabled = true
        startAutoSkipCountdown()
    }
    private fun startAutoSkipCountdown() {
        cancelAutoSkip() // Cancel any existing countdown

        countdownSeconds = 3
        tvCountdown.visibility = View.VISIBLE
        tvCountdown.text = "Next song in $countdownSeconds..."

        autoSkipRunnable = object : Runnable {
            override fun run() {
                countdownSeconds--

                if (countdownSeconds > 0) {
                    tvCountdown.text = "Next song in $countdownSeconds..."
//                    showFeedback(
//                        "Correct: \"${currentSong.movieName}\"",
//                        "#F44336"
//                    )
                    handler.postDelayed(this, 1000)
                } else {
                    tvCountdown.visibility = View.GONE
                    autoSkipToNextSong()
                }
            }
        }
        handler.postDelayed(autoSkipRunnable!!, 1000)
    }

    private fun cancelAutoSkip() {
        autoSkipRunnable?.let { handler.removeCallbacks(it) }
        tvCountdown.visibility = View.GONE
    }

    private fun autoSkipToNextSong() {
        showFeedback("⏭️ Auto-skipped!", "#FFA500")
        releaseMediaPlayer()
        stopProgressBar()

        handler.postDelayed({
            nextTurn()
        }, 1500)
    }

//    private fun repeatSong() {
//        cancelAutoSkip() // Cancel auto-skip if user wants to repeat
//        val currentSong = songs[currentSongIndex]
//        playSong(currentSong)
//
//    }

//    private fun skipSong() {
//        // No points change for skip
//        showFeedback("⏭️ Skipped!", "#FFA500")
//
//        releaseMediaPlayer()
//        stopProgressBar()
//
//        handler.postDelayed({
//            nextTurn()
//        }, 2000)
//    }

    private fun startVoiceRecognition() {

        isWaitingForAnswer = true
        voiceRecognizer.startListening()
    }


    private fun processVoiceCommand(text: String) {
        val lowerText = text.lowercase().trim()
        android.util.Log.d("SongGame", "Voice command: $lowerText")

        // Manual button mode - process raw text as answer directly
        if (micMode == MicMode.MANUAL_BUTTON) {
            android.util.Log.d("SongGame", "Manual mode - processing answer: $lowerText")
            processAnswer(lowerText)
            return  // Exit early
        }


        // Always-on mode - need wake words
        // Check for "stop" command first (only if song is playing)
//        if (lowerText.contains("stop") && isSongPlaying) {
//            android.util.Log.d("SongGame", "Stop command detected")
//            stopSong()
//            return
//        }

        // Check for "movie [name]" command
//        if (lowerText.startsWith("movie ")) {
//            android.util.Log.d("SongGame", "Movie command detected")
//
//            // Stop song if still playing
//            if (isSongPlaying) {
//                stopSong()
//            }
//
//            val movieName = lowerText.removePrefix("movie ").trim()
//            if (movieName.isNotEmpty()) {
//                processAnswer(movieName)
//            } else {
//                Toast.makeText(this, "Please say the movie name after 'movie'", Toast.LENGTH_SHORT).show()
//            }
//        }

        val hasStopWord = lowerText.contains("stop")
        val hasMovieWord = lowerText.startsWith("movie ")

        if ((hasStopWord || hasMovieWord) && isSongPlaying) {
            // IMMEDIATELY pause the song
            mediaPlayer?.pause()
            isSongPlaying = false
            stopProgressBar()
            tvSongStatus.text = "⏸️ Song Paused"
            android.util.Log.d("SongGame", "Song instantly paused")
        }

        // Now process the command
        if (hasStopWord) {
            android.util.Log.d("SongGame", "Stop command detected")
            stopSong()
            return
        }

        if (hasMovieWord) {
            android.util.Log.d("SongGame", "Movie command detected")
            val movieName = lowerText.removePrefix("movie ").trim()

            if (movieName.isNotEmpty()) {
                processAnswer(movieName)
            } else {
                Toast.makeText(this, "Please say the movie name after 'movie'", Toast.LENGTH_SHORT).show()
                // Allow them to continue
                if (!isSongPlaying && mediaPlayer != null) {
                    mediaPlayer?.start()
                    isSongPlaying = true
                    startProgressBar()
                }
            }
        }


    }

    private fun stopSong() {
//        if (isSongPlaying) {
//            mediaPlayer?.pause()
//            isSongPlaying = false
//            stopProgressBar()}

        if (!isSongPlaying && mediaPlayer != null) {
            // Already paused, just update UI
            tvSongStatus.text = "⏸️ Song Paused"
        }

            if (micMode == MicMode.MANUAL_BUTTON) {
                tvInstruction.text = "Press button to say answer"
              //  btnRepeat.visibility = View.VISIBLE
               // btnSkip.visibility = View.VISIBLE
            } else {
                tvInstruction.text = "Say 'movie [name]' to answer"
            }

         //   btnRepeat.isEnabled = true
           // btnSkip.isEnabled = true

            // Switch to answer mode
//            isListeningForStop = false
//            isListeningForAnswer = true

            Toast.makeText(this, "Song stopped! Say your answer", Toast.LENGTH_SHORT).show()

    }

    private fun processAnswer(movieName: String) {
        cancelAutoSkip() // Cancel auto-skip when answer is given
        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()
        }
        releaseMediaPlayer()
        stopProgressBar()

        val currentSong = songs[currentSongIndex]
        val (isCorrect, matchedSong) = songDatabase.validateSong(movieName, currentSong, usedSongs)

        if (isCorrect && matchedSong != null) {
            // Correct answer
            teams[currentTeamIndex].score += 1
            usedSongs.add(movieName)
            usedSongs.addAll(currentSong.variations)

            showFeedback("✅ Correct! \"${matchedSong.movieName}\"", "#4CAF50")
            updateUI()
            //checkWinCondition()
            // Check win condition but don't block nextTurn
            val currentTeam = teams[currentTeamIndex]
            if (currentTeam.score >= winningScore) {
                isWaitingForAnswer = false
                voiceRecognizer.stopListening()
                handler.postDelayed({
                    declareWinner()
                }, 2000)
                return  // Exit early, don't call nextTurn
             }


        } else {
            // Wrong answer
            teams[currentTeamIndex].score -= 1
            //showFeedback("❌ Already used!", "#F44336")
            showFeedback(
                "❌ Wrong!\nYou said: \"$movieName\"\nCorrect: \"${currentSong.movieName}\"",
                "#F44336"
            )

//            if (usedSongs.any { it.lowercase() == movieName.lowercase() }) {
//                showFeedback("❌ Already used!", "#F44336")
//            } else {
//                showFeedback(
//                    "❌ Wrong!\nYou said: \"$movieName\"\nCorrect: \"${currentSong.movieName}\"",
//                    "#F44336"
//                ) }
            updateUI()

        }

        handler.postDelayed({
            nextTurn()
        }, 2000)
    }

    private fun showFeedback(message: String, colorHex: String) {
        tvFeedback.text = message
        tvFeedback.setTextColor(android.graphics.Color.parseColor(colorHex))
        tvFeedback.visibility = View.VISIBLE
    }

    private fun nextTurn() {
        tvFeedback.visibility = View.INVISIBLE
        currentTeamIndex = (currentTeamIndex + 1) % teams.size
        currentSongIndex++

        updateUI()
        // Load next song
        loadAndPlayFirstSong()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            isWaitingForAnswer = true
            handler.postDelayed({
                voiceRecognizer.startListening()
            }, 1000)
        }
    }

//    private fun checkWinCondition() {
//        val currentTeam = teams[currentTeamIndex]
//        if (currentTeam.score >= winningScore) {
//            isWaitingForAnswer = false
//            stopSong()
//            voiceRecognizer.stopListening()
//            releaseMediaPlayer()
//
//            handler.postDelayed({
//                declareWinner()
//            }, 3000)
//        }
//    }

    private fun declareWinner() {
        isWaitingForAnswer = false
        voiceRecognizer.stopListening()
        releaseMediaPlayer()

        val winner = teams.maxByOrNull { it.score } ?: teams[0]

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winnerName", winner.name)
        intent.putExtra("team1Name", teams[0].name)
        intent.putExtra("team1Score", teams[0].score)
        intent.putExtra("team2Name", teams[1].name)
        intent.putExtra("team2Score", teams[1].score)
        startActivity(intent)
        finish()

//        // Show winner feedback
//        showFeedback("🏆 ${winner.name} WINS! 🏆", "#FFD700")
//        tvSongStatus.text = "🎉 Game Over!"
//        tvInstruction.text = "Returning to home..."
//
//
//        // Play winner song
//        playWinnerSong()
//
//        // Navigate to home after 3 seconds
//        handler.postDelayed({
//            winnerMediaPlayer?.release()
//            winnerMediaPlayer = null
//
//            // Navigate to home screen (MainActivity)
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finish()
//        }, 3000)


    }

//    private fun playWinnerSong() {
//        try {
//            val resourceId = resources.getIdentifier("winner_song", "raw", packageName)
//
//            if (resourceId != 0) {
//                winnerMediaPlayer = MediaPlayer.create(this, resourceId)
//                winnerMediaPlayer?.setVolume(0.8f, 0.8f)
//                winnerMediaPlayer?.start()
//
//                // Stop after 3 seconds
//                handler.postDelayed({
//                    winnerMediaPlayer?.stop()
//                    winnerMediaPlayer?.release()
//                    winnerMediaPlayer = null
//                }, 3000)
//            } else {
//                android.util.Log.w("SongGame", "winner_song.mp3 not found in res/raw/")
//            }
//        } catch (e: Exception) {
//            android.util.Log.e("SongGame", "Error playing winner song: ${e.message}")
//        }
    //}

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
            tvTapHint.text = "🎤 Listening..."
        } else {
            imgMic.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            tvTapHint.text = "Mic ready"
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
        isSongPlaying = false
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        voiceRecognizer.stopListening()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
        cancelAutoSkip()
    }

    override fun onResume() {
        super.onResume()
        if (isSongPlaying && mediaPlayer != null) {
            mediaPlayer?.start()
        }
        if (micMode == MicMode.ALWAYS_ON_WAKEWORD && isWaitingForAnswer) {
            startMicMonitoring()
            handler.postDelayed({ voiceRecognizer.startListening() }, 500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isWaitingForAnswer = false
        voiceRecognizer.destroy()
        releaseMediaPlayer()
        winnerMediaPlayer?.release()
        stopProgressBar()
        cancelAutoSkip()
        micCheckRunnable?.let { handler.removeCallbacks(it) }
        buttonTimerRunnable?.let { handler.removeCallbacks(it) }
    }
}