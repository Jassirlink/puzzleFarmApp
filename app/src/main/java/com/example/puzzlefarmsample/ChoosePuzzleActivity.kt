package com.example.puzzlefarmsample

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoosePuzzleActivity : AppCompatActivity() {

    lateinit var sfx_player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_difficulty)

//        sfx_player = Sfx.getSfxPlayer(this)!!
    }

    fun onClick(v: View) {
        /*if (Settings.getValue("sounds").equals("on"))
        {
            sfx_player.start()
        }*/

        val intent = Intent(this, PuzzleActivity::class.java)
        when(v) {
            findViewById<Button>(R.id.btnPuzzle1) -> {
                intent.putExtra("Columns", 3)
                intent.putExtra("Puzzle", (1..8).random())
                intent.putExtra("PuzzleName", "3x3")
                startActivity(intent)

            }
            findViewById<Button>(R.id.btnPuzzle2) -> {
                intent.putExtra("Columns", 4)
                intent.putExtra("Puzzle", (1..8).random())
                intent.putExtra("PuzzleName", "4x4")
                startActivity(intent)
            }
            findViewById<Button>(R.id.btnPuzzle3) -> {
                intent.putExtra("Columns", 5)
                intent.putExtra("Puzzle", (1..8).random())
                intent.putExtra("PuzzleName", "5x5")
                startActivity(intent)
            }
        }

    }
}