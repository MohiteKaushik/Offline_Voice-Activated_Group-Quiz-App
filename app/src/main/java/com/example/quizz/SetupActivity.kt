package com.example.quizz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SetupActivity : AppCompatActivity() {

    private lateinit var etTeam1: EditText
    private lateinit var etTeam2: EditText
    private lateinit var etWinningScore: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        etTeam1 = findViewById(R.id.etTeam1)
        etTeam2 = findViewById(R.id.etTeam2)
        etWinningScore = findViewById(R.id.etWinningScore)

        findViewById<Button>(R.id.btnContinue).setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putExtra("team1", etTeam1.text.toString())
                intent.putExtra("team2", etTeam2.text.toString())
                intent.putExtra("winningScore", etWinningScore.text.toString().toInt())
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val team1 = etTeam1.text.toString().trim()
        val team2 = etTeam2.text.toString().trim()
        val score = etWinningScore.text.toString().trim()

        return when {
            team1.isEmpty() -> {
                Toast.makeText(this, "Enter Team 1 name", Toast.LENGTH_SHORT).show()
                false
            }
            team2.isEmpty() -> {
                Toast.makeText(this, "Enter Team 2 name", Toast.LENGTH_SHORT).show()
                false
            }
            score.isEmpty() -> {
                Toast.makeText(this, "Enter winning score", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}