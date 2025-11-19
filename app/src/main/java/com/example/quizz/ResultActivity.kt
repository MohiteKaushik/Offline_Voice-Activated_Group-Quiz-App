package com.example.quizz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var winnerMediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        playWinnerSong()

        val winnerName = intent.getStringExtra("winnerName")?: "Winner"
        val team1Name = intent.getStringExtra("team1Name") ?: "Team A"
        val team1Score = intent.getIntExtra("team1Score", 0)
        val team2Name = intent.getStringExtra("team2Name") ?: "Team B"
        val team2Score = intent.getIntExtra("team2Score", 0)

        findViewById<TextView>(R.id.tvWinner).text = "$winnerName Wins!"
        findViewById<TextView>(R.id.tvTeam1Result).text = "$team1Name: $team1Score"
        findViewById<TextView>(R.id.tvTeam2Result).text = "$team2Name: $team2Score"



        findViewById<Button>(R.id.btnPlayAgain).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun playWinnerSong() {
        try {
            val resourceId = resources.getIdentifier("winner_song", "raw", packageName)

            if (resourceId != 0) {
                winnerMediaPlayer = MediaPlayer.create(this, resourceId)
                winnerMediaPlayer?.setVolume(0.8f, 0.8f)
                winnerMediaPlayer?.start()

                // Stop after 3 seconds
                handler.postDelayed({
                    winnerMediaPlayer?.stop()
                    winnerMediaPlayer?.release()
                    winnerMediaPlayer = null
                }, 3000)
            } else {
                android.util.Log.w("SongGame", "winner_song.mp3 not found in res/raw/")
            }
        } catch (e: Exception) {
            android.util.Log.e("SongGame", "Error playing winner song: ${e.message}")
        }
    }
}