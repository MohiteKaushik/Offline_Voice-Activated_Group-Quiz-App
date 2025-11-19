// ============================================
// GuessHeroActivity.kt - Guess Hero Names
// ============================================
package com.example.quizz

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.models.MicMode
import com.example.quizz.models.Team
import com.example.quizz.utils.HeroDatabase
import com.example.quizz.utils.VoiceRecognizer

class GuessHeroActivity : AppCompatActivity() {

    private lateinit var teams: List<Team>
    private var currentTeamIndex = 0
    private var winningScore = 20
    private lateinit var heroDatabase: HeroDatabase
    private var usedHeroes = mutableSetOf<String>()
    private lateinit var micMode: MicMode
    private val heroNames = mutableListOf<String>()

    private lateinit var voiceRecognizer: VoiceRecognizer
    private var isWaitingForAnswer = false
    private var buttonMicActive = false

    private lateinit var tvTeam1Name: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Name: TextView
    private lateinit var tvTeam2Score: TextView
    private lateinit var tvCurrentTurn: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var llHeroList: LinearLayout
    private lateinit var imgMic: ImageView
    private lateinit var btnSpeak: Button

    private val handler = Handler(Looper.getMainLooper())
    private var micCheckRunnable: Runnable? = null
    private var buttonTimerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_hero)

        initializeViews()
        setupGame()
        setupVoiceRecognizer()
        displayHeroNames()

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
        tvInstruction = findViewById(R.id.tvInstruction)
        tvFeedback = findViewById(R.id.tvFeedback)
        llHeroList = findViewById(R.id.llHeroList)
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
        heroNames.addAll(heroDatabase.getAllHeroNames())

        if (micMode == MicMode.MANUAL_BUTTON) {
            btnSpeak.visibility = View.VISIBLE
            tvInstruction.text = "Press button and say 'hero [name]'"
        } else {
            btnSpeak.visibility = View.GONE
            tvInstruction.text = "Say 'hero [name]' anytime"
        }
        // Initially show empty list (no heroes guessed yet)
        updateUsedHerosList()
        updateUI()
    }

    private fun setupVoiceRecognizer() {
        voiceRecognizer = VoiceRecognizer(
            context = this,
            onResult = { recognizedText ->
                runOnUiThread {
                    android.util.Log.d("GuessHero", "Received: $recognizedText")

                    if (micMode == MicMode.MANUAL_BUTTON) {
                        processAnswer(recognizedText.trim())
                    } else {
                        // In always-on mode, wake word required
                        if (recognizedText.startsWith("hero ")) {
                            val heroName = recognizedText.removePrefix("hero ").trim()
                            processAnswer(heroName)
                        }
                    }
                }
            },
            onError = { error ->
                runOnUiThread {
                    android.util.Log.e("GuessHero", "Voice error: $error")
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

    private fun displayHeroNames() {
//        llHeroList.removeAllViews()
//
//        heroNames.forEach { heroName ->
//            val textView = TextView(this).apply {
//                text = heroName
//                textSize = 18f
//                setTextColor(ContextCompat.getColor(this@GuessHeroActivity, android.R.color.white))
//                setPadding(16, 12, 16, 12)
//
//                // Check if used
//                val isUsed = usedHeroes.any { it.lowercase() == heroName.lowercase() }
//                if (isUsed) {
//                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    setTextColor(ContextCompat.getColor(this@GuessHeroActivity, android.R.color.darker_gray))
//                }
//            }
//
//            llHeroList.addView(textView)
//        }
    }
    private fun updateUsedHerosList() {
        llHeroList.removeAllViews()

        if (usedHeroes.isEmpty()) {
            // Show message when no heroes guessed yet
            val textView = TextView(this).apply {
                text = "No heroes guessed yet..."
                textSize = 16f
                setTextColor(ContextCompat.getColor(this@GuessHeroActivity, android.R.color.darker_gray))
                setPadding(16, 12, 16, 12)
                gravity = android.view.Gravity.CENTER
            }
            llHeroList.addView(textView)
            return
        }

        // Title
        val titleView = TextView(this).apply {
            text = "Heroes Guessed (${usedHeroes.size}):"
            textSize = 18f
            setTextColor(ContextCompat.getColor(this@GuessHeroActivity, android.R.color.white))
            setPadding(16, 16, 16, 8)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        llHeroList.addView(titleView)

        // Show only guessed heroes
        val guessedHeroNames = heroes.filter { hero ->
            usedHeroes.any { used ->
                hero.aliases.any { it.lowercase() == used.lowercase() } ||
                        hero.name.lowercase() == used.lowercase()
            }
        }.map { it.name }

        guessedHeroNames.forEach { heroName ->
            val textView = TextView(this).apply {
                text = "✓ $heroName"
                textSize = 18f
                setTextColor(ContextCompat.getColor(this@GuessHeroActivity, android.R.color.holo_green_light))
                setPadding(24, 12, 16, 12)
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            llHeroList.addView(textView)
        }
    }

    private val heroes by lazy { heroDatabase.getAllHeroes() }

    private fun processAnswer(heroName: String) {
        android.util.Log.d("GuessHero", "Processing answer: $heroName")
        if (micMode == MicMode.MANUAL_BUTTON) {
            deactivateButtonMic()
        } else {
            isWaitingForAnswer = false
            voiceRecognizer.stopListening()
        }

        val (isValid, hero) = heroDatabase.validateHero(heroName, usedHeroes)

        if (isValid && hero != null) {
            // Correct answer
            teams[currentTeamIndex].score += 1
            usedHeroes.add(heroName)
            usedHeroes.addAll(hero.aliases)

            showFeedback("✅ Correct! \"${hero.name}\"", "#4CAF50")

            // Update display
           // displayHeroNames()
            // Update the guessed heroes list
            updateUsedHerosList()

            // Check if all heroes guessed
            if (usedHeroes.size >= heroNames.size) {
                Toast.makeText(this, "All heroes guessed!", Toast.LENGTH_SHORT).show()
            }

            checkWinCondition()
        } else {
            // Wrong answer
            teams[currentTeamIndex].score -= 1

            if (usedHeroes.any { it.lowercase() == heroName.lowercase() }) {
                showFeedback("❌ Already used!", "#F44336")
            } else {
                showFeedback("❌ You said: \"$heroName\"\nNot a valid hero name!", "#F44336")
            }
        }

        updateUI()

        handler.postDelayed({
            tvFeedback.visibility = View.INVISIBLE
            nextTurn()
        }, 3000)
    }

    private fun showFeedback(message: String, colorHex: String) {
        tvFeedback.text = message
        tvFeedback.setTextColor(android.graphics.Color.parseColor(colorHex))
        tvFeedback.visibility = View.VISIBLE
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