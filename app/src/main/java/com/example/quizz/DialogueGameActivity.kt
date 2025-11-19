// ============================================
// DialogueGameActivity.kt - Guess Movie by Dialogue
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
import com.example.quizz.models.Dialogue
import com.example.quizz.models.Team
import com.example.quizz.utils.DialogueDatabase
import com.example.quizz.utils.VoiceRecognizer

class DialogueGameActivity : AppCompatActivity() {

    // Game State
    private lateinit var teams: List<Team>
    private var currentTeamIndex = 0
    private var winningScore = 20
    private lateinit var dialogueDatabase: DialogueDatabase
    private var dialogues = mutableListOf<Dialogue>()
    private var currentDialogueIndex = 0
    private var usedDialogues = mutableSetOf<String>()

    // Audio
    private var mediaPlayer: MediaPlayer? = null
    private var winnerMediaPlayer: MediaPlayer? = null
    private var isDialoguePlaying = false
    private var dialogueVolume = 0.5f
    private lateinit var micMode: MicMode

    // Voice Recognition
    private lateinit var voiceRecognizer: VoiceRecognizer
    private var isWaitingForAnswer = false
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
    private lateinit var tvDialogueStatus: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var imgMic: ImageView
    private lateinit var btnSpeak: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTapHint: TextView
    private lateinit var tvCountdown: TextView

    // After existing properties
    private lateinit var btnRepeat: Button
    private var currentSoundType: String = "" // "dialogue" or "meme"

    private val handler = Handler(Looper.getMainLooper())
    private var progressUpdateRunnable: Runnable? = null
    private var micCheckRunnable: Runnable? = null
    private var buttonTimerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogue_game)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
        startMicMonitoring()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            startAlwaysOnMode()
        }

        loadAndPlayFirstDialogue()
    }

    private fun initializeViews() {
        tvTeam1Name = findViewById(R.id.tvTeam1Name)
        tvTeam1Score = findViewById(R.id.tvTeam1Score)
        tvTeam2Name = findViewById(R.id.tvTeam2Name)
        tvTeam2Score = findViewById(R.id.tvTeam2Score)
        tvCurrentTurn = findViewById(R.id.tvCurrentTurn)
        tvDialogueStatus = findViewById(R.id.tvDialogueStatus)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvFeedback = findViewById(R.id.tvFeedback)
        imgMic = findViewById(R.id.imgMic)
        progressBar = findViewById(R.id.progressBar)
        tvTapHint = findViewById(R.id.tvTapHint)
        btnSpeak = findViewById(R.id.btnSpeak)
        btnRepeat = findViewById(R.id.btnRepeat)
        tvCountdown = findViewById(R.id.tvCountdown)

        btnSpeak.setOnClickListener { activateButtonMic() }
        btnRepeat.setOnClickListener { repeatCurrentSound() }
    }

    private fun setupGame() {
        val team1Name = intent.getStringExtra("team1") ?: "Team A"
        val team2Name = intent.getStringExtra("team2") ?: "Team B"
        winningScore = intent.getIntExtra("winningScore", 20)
        micMode = MicMode.valueOf(intent.getStringExtra("micMode") ?: "ALWAYS_ON_WAKEWORD")

        teams = listOf(Team(team1Name), Team(team2Name))

        dialogueDatabase = DialogueDatabase()
        dialogues = dialogueDatabase.getAllDialogues().toMutableList()

        if (dialogues.isEmpty()) {
            Toast.makeText(this, "No dialogues available! Add dialogues to res/raw/", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (micMode == MicMode.MANUAL_BUTTON) {
            btnSpeak.visibility = View.VISIBLE
            tvTapHint.visibility = View.GONE
            tvInstruction.text = "Press button to say answer"
        } else {
            btnSpeak.visibility = View.GONE
            tvInstruction.text = "Say 'movie [name]' to answer"
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
                    android.util.Log.d("DialogueGame", "Received: $recognizedText")
                    processVoiceCommand(recognizedText)
                }
            },
            onError = { error ->
                runOnUiThread {
                    android.util.Log.e("DialogueGame", "Voice error: $error")
                }
            },
            onListeningStateChange = { isListening ->
                runOnUiThread {
                    updateMicIcon(isListening)
                }
            }
        )
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
        if (isWaitingForAnswer) {
            voiceRecognizer.stopListening()
            handler.postDelayed({
                voiceRecognizer.startListening()
            }, 500)
        }
    }

    private fun activateButtonMic() {
        if (isDialoguePlaying) {
            mediaPlayer?.pause()
            isDialoguePlaying = false
            stopProgressBar()
            tvDialogueStatus.text = "⏸️ Listening..."
        }

        if (buttonMicActive) return

        buttonMicActive = true
        btnSpeak.text = "LISTENING..."
        btnSpeak.isEnabled = false

        voiceRecognizer.startListening()

        buttonTimerRunnable = Runnable {
            if (buttonMicActive) {
                deactivateButtonMic()
                Toast.makeText(this, "No answer detected. Try again.", Toast.LENGTH_SHORT).show()
                if (mediaPlayer != null && !isDialoguePlaying) {
                    mediaPlayer?.start()
                    isDialoguePlaying = true
                    startProgressBar()
                    tvDialogueStatus.text = "🎬 Dialogue Playing..."
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

    private fun loadAndPlayFirstDialogue() {
        if (currentDialogueIndex >= dialogues.size) {
            Toast.makeText(this, "All dialogues completed!", Toast.LENGTH_SHORT).show()
            declareWinner()
            return
        }

        val currentDialogue = dialogues[currentDialogueIndex]
        playDialogue(currentDialogue)
        val isMeme = (0..1).random() == 1
        currentSoundType = if (isMeme && currentDialogue.memeResourceName != null) "meme" else "dialogue"

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            startVoiceRecognition()
        }
    }

    private fun playDialogue(dialogue: Dialogue) {
        try {
            releaseMediaPlayer()
            // Choose resource based on current sound type
            val resourceNames = if (currentSoundType == "meme" && dialogue.memeResourceName != null) {
                dialogue.memeResourceName
            } else {
                dialogue.resourceName
            }


            val resourceId = resources.getIdentifier(resourceNames, "raw", packageName)

            if (resourceId == 0) {
                Toast.makeText(this, "Sound file not found: ${resourceNames}.mp3", Toast.LENGTH_LONG).show()
                autoSkipToNext()
                return
            }

            mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer?.setVolume(dialogueVolume, dialogueVolume)

            mediaPlayer?.setOnCompletionListener {
                onDialogueComplete()
            }

            mediaPlayer?.start()
            isDialoguePlaying = true

            // Update status text based on sound type
            val emoji = if (currentSoundType == "meme") "😁" else "🎬"
            tvDialogueStatus.text = "$emoji ${if (currentSoundType == "meme") "Meme" else "Dialogue"} Playing..."

            if (micMode == MicMode.MANUAL_BUTTON) {
                tvInstruction.text = "Press button to say answer"
                btnSpeak.isEnabled = true
                btnSpeak.visibility = View.VISIBLE
                btnRepeat.visibility = View.GONE  // Hide repeat while playing
            } else {
                tvInstruction.text = "Say 'movie [name]' to answer"
                btnRepeat.visibility = View.GONE  // Hide repeat while playing
            }

            startProgressBar()

        } catch (e: Exception) {
            Toast.makeText(this, "Error playing Sound: ${e.message}", Toast.LENGTH_SHORT).show()
            autoSkipToNext()
        }
    }

    private fun startProgressBar() {
        progressBar.max = mediaPlayer?.duration ?: 30000
        progressBar.progress = 0

        progressUpdateRunnable = object : Runnable {
            override fun run() {
                if (mediaPlayer != null && isDialoguePlaying) {
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

    private fun onDialogueComplete() {
        isDialoguePlaying = false
        stopProgressBar()
        progressBar.progress = progressBar.max

        val emoji = if (currentSoundType == "meme") "😁" else "🎬"
        tvDialogueStatus.text = "🔇 ${if (currentSoundType == "meme") "Meme" else "Dialogue"} Ended"

        // Show feedback after 2.5 seconds delay
        handler.postDelayed({
            val currentDialogue = dialogues[currentDialogueIndex]
            showFeedback("$emoji Movie: ${currentDialogue.movieName}", "#FFD700")
        }, 2500)

        if (micMode == MicMode.MANUAL_BUTTON) {
            tvInstruction.text = "Time's up! Moving to next dialogue..."
           // btnSpeak.isEnabled = false
            btnSpeak.isEnabled = true
            btnSpeak.visibility = View.VISIBLE
            btnRepeat.visibility = View.VISIBLE  // Show repeat button
        } else {
            tvInstruction.text = "Time's up! Moving to next dialogue..."
            btnRepeat.visibility = View.VISIBLE  // Show repeat button
        }

        startAutoSkipCountdown()
    }

    private fun startAutoSkipCountdown() {
        cancelAutoSkip()

        countdownSeconds = 5
        tvCountdown.visibility = View.VISIBLE
        tvCountdown.text = "Skipping in $countdownSeconds..."

        autoSkipRunnable = object : Runnable {
            override fun run() {
                countdownSeconds--

                if (countdownSeconds > 0) {
                    tvCountdown.text = "Skipping in $countdownSeconds..."
                    handler.postDelayed(this, 1000)
                } else {
                    tvCountdown.visibility = View.GONE
                    autoSkipToNext()
                }
            }
        }
        handler.postDelayed(autoSkipRunnable!!, 2000)
    }

    private fun cancelAutoSkip() {
        autoSkipRunnable?.let { handler.removeCallbacks(it) }
        tvCountdown.visibility = View.GONE
    }

    private fun autoSkipToNext() {
        showFeedback("⏭️ Auto-skipped!", "#FFA500")
        releaseMediaPlayer()
        stopProgressBar()

        handler.postDelayed({
            nextTurn()
        }, 1000)
    }

    private fun repeatCurrentSound() {
        cancelAutoSkip()
        btnRepeat.visibility = View.GONE
        val currentDialogue = dialogues[currentDialogueIndex]
        playDialogue(currentDialogue)
    }

    private fun startVoiceRecognition() {
        isWaitingForAnswer = true
        voiceRecognizer.startListening()
    }

    private fun processVoiceCommand(text: String) {
        val lowerText = text.lowercase().trim()
        android.util.Log.d("DialogueGame", "Voice command: $lowerText")

        if (micMode == MicMode.MANUAL_BUTTON) {
            android.util.Log.d("DialogueGame", "Manual mode - processing: $lowerText")
            processAnswer(lowerText)
            return
        }

        // Check for "repeat" command when sound has ended
        if (lowerText == "repeat" && !isDialoguePlaying) {
            repeatCurrentSound()
            return
        }

        // Instant pause for wake words
        val hasMovieWord = lowerText.startsWith("movie ")

        if (hasMovieWord && isDialoguePlaying) {
            mediaPlayer?.pause()
            isDialoguePlaying = false
            stopProgressBar()
            tvDialogueStatus.text = "⏸️ Dialogue Paused"
        }

        if (hasMovieWord) {
            val movieName = lowerText.removePrefix("movie ").trim()

            if (movieName.isNotEmpty()) {
                processAnswer(movieName)
            } else {
                Toast.makeText(this, "Please say the movie name after 'movie'", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processAnswer(movieName: String) {
        cancelAutoSkip()
        btnRepeat.visibility = View.GONE

        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()
        }

        releaseMediaPlayer()
        stopProgressBar()

        val currentDialogue = dialogues[currentDialogueIndex]
        val (isCorrect, matchedDialogue) = dialogueDatabase.validateDialogue(movieName, currentDialogue, usedDialogues)

        if (isCorrect && matchedDialogue != null) {
            teams[currentTeamIndex].score += 1
            usedDialogues.add(movieName)
            usedDialogues.addAll(currentDialogue.variations)

            showFeedback("✅ Correct! \"${matchedDialogue.movieName}\"", "#4CAF50")
            updateUI()

            val currentTeam = teams[currentTeamIndex]
            if (currentTeam.score >= winningScore) {
                isWaitingForAnswer = false
                voiceRecognizer.stopListening()
                handler.postDelayed({
                    declareWinner()
                }, 2000)
                return
            }
        } else {
            teams[currentTeamIndex].score -= 1

            if (usedDialogues.any { it.lowercase() == movieName.lowercase() }) {
                showFeedback("❌ Already used!", "#F44336")
            } else {
                showFeedback(
                    "❌ Wrong!\nYou said: \"$movieName\"\nCorrect: \"${currentDialogue.movieName}\"",
                    "#F44336"
                )
            }
            updateUI()
        }

        handler.postDelayed({
            nextTurn()
        }, 1500)
    }

    private fun showFeedback(message: String, colorHex: String) {
        tvFeedback.text = message
        tvFeedback.setTextColor(android.graphics.Color.parseColor(colorHex))
        tvFeedback.visibility = View.VISIBLE
    }

    private fun nextTurn() {
        tvFeedback.visibility = View.INVISIBLE
        currentTeamIndex = (currentTeamIndex + 1) % teams.size
        currentDialogueIndex++

        updateUI()
        loadAndPlayFirstDialogue()

        if (micMode == MicMode.ALWAYS_ON_WAKEWORD) {
            isWaitingForAnswer = true
            handler.postDelayed({
                voiceRecognizer.startListening()
            }, 1000)
        }
    }

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
        isDialoguePlaying = false
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
        if (isDialoguePlaying && mediaPlayer != null) {
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