// ============================================
// CategoryActivity.kt (COMPLETELY UPDATED)
// ============================================
package com.example.quizz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizz.models.GameCategory
import com.example.quizz.models.MicMode

class CategoryActivity : AppCompatActivity() {

    private lateinit var rgCategory: RadioGroup
    private lateinit var rgMicMode: RadioGroup
    private var selectedCategory: GameCategory? = null
    private var selectedMicMode: MicMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        rgCategory = findViewById(R.id.rgCategory)
        rgMicMode = findViewById(R.id.rgMicMode)

        // Category selection
        // Category selection with visual feedback
        rgCategory.setOnCheckedChangeListener { group, checkedId ->
            // Reset all radio buttons to white text
            for (i in 0 until group.childCount) {
                (group.getChildAt(i) as? RadioButton)?.setTextColor(
                    resources.getColor(android.R.color.white, null)
                )
            }
            // Highlight selected with dark text
            findViewById<RadioButton>(checkedId)?.setTextColor(
                resources.getColor(android.R.color.black, null)
            )

            selectedCategory = when(checkedId) {
                R.id.rbHeroMovies -> GameCategory.HERO_MOVIES
                R.id.rbGuessbyImage -> GameCategory.GUESS_BY_IMAGE
                R.id.rbGuessBySong -> GameCategory.GUESS_BY_SONG
                R.id.rbGuessByDialogue -> GameCategory.GUESS_BY_DIALOGUE
                R.id.rbGuessHero -> GameCategory.GUESS_HERO_NAMES
                else -> null
            }
        }

        // Mic mode selection with visual feedback
        rgMicMode.setOnCheckedChangeListener { group, checkedId ->
            // Reset all radio buttons to white text
            for (i in 0 until group.childCount) {
                (group.getChildAt(i) as? RadioButton)?.setTextColor(
                    resources.getColor(android.R.color.white, null)
                )
            }
            // Highlight selected with dark text
            findViewById<RadioButton>(checkedId)?.setTextColor(
                resources.getColor(android.R.color.black, null)
            )

            selectedMicMode = when(checkedId) {
                R.id.rbManualButton -> MicMode.MANUAL_BUTTON
                R.id.rbAlwaysOnWakeword -> MicMode.ALWAYS_ON_WAKEWORD
                else -> null
            }
        }

        findViewById<Button>(R.id.btnStartGame).setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        if (selectedCategory == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedMicMode == null) {
            Toast.makeText(this, "Please select a mic mode", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = when(selectedCategory) {
            GameCategory.HERO_MOVIES -> Intent(this, HeroMovieActivity::class.java)
            GameCategory.GUESS_BY_IMAGE -> Intent(this, ImageGuessActivity::class.java)
            GameCategory.GUESS_BY_SONG -> Intent(this, SongGameActivity::class.java)
            GameCategory.GUESS_BY_DIALOGUE -> Intent(this, DialogueGameActivity::class.java)  // NEW
            GameCategory.GUESS_HERO_NAMES -> Intent(this, GuessHeroActivity::class.java)
            else -> return
        }

        intent.putExtra("team1", getIntent().getStringExtra("team1"))
        intent.putExtra("team2", getIntent().getStringExtra("team2"))
        intent.putExtra("winningScore", getIntent().getIntExtra("winningScore", 20))
        intent.putExtra("category", selectedCategory!!.name)
        intent.putExtra("micMode", selectedMicMode!!.name)

        startActivity(intent)
        finish()
    }
}