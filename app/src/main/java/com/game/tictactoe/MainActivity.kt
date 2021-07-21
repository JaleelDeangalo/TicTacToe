package com.game.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private var gameOverCounter: Int = 0
        private lateinit var gameState: IntArray
        private lateinit var winningStates: Array<IntArray>
        private var gameOver = false
        private var activePlayer = 0
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

         winningStates = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )
    }


    fun replay(view: View?) {
        Log.w(TAG, "replay: ${view.toString()}", null)
        val winnerDisplay: TextView = findViewById(R.id.winnerDisplay)
        val replayBtn: Button = findViewById(R.id.replayBtn)
        winnerDisplay.visibility = View.INVISIBLE
        replayBtn.visibility = View.INVISIBLE
        val gridLayout: GridLayout= findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val counter = gridLayout.getChildAt(i) as ImageView
            counter.setImageDrawable(null)
        }
        Arrays.fill(gameState, 2)
        activePlayer = 0
        gameOver = false
    }


    fun dropIn(view: View) {

        val counter = view as ImageView

        counter.tag

        val tappedCounter = counter.tag.toString().toInt()

        if (gameState[tappedCounter] == 2 && !gameOver) {
            gameState[tappedCounter] = activePlayer
            counter.translationY = -1500f
            activePlayer = if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red)
                1
            } else {
                counter.setImageResource(R.drawable.yellow)
                0
            }
            counter.animate().translationYBy(1500f).duration = 300
            if (gameOverCounter < 8) {
                gameOverCounter += 1
            } else {
                val winner = "Game Is A Draw"
                val winnerDisplay: TextView = findViewById(R.id.winnerDisplay)
                val replayBtn: Button = findViewById(R.id.replayBtn)
                winnerDisplay.text = winner
                gameOverCounter = 0
                replayBtn.visibility = View.VISIBLE
                winnerDisplay.visibility = View.VISIBLE
            }
            for (winningState in winningStates) {
                if (gameState[winningState[0]] == gameState[winningState[1]]
                    && gameState[winningState[1]] == gameState[winningState[2]]
                    && gameState[winningState[0]] != 2) {
                    gameOver = true
                    gameOverCounter = 0
                    val winner: String = if (activePlayer == 0) {
                        "Yellow Has Won"
                    } else {
                        "Red Has Won"
                    }
                    val winnerDisplay: TextView = findViewById(R.id.winnerDisplay)
                    val replayBtn: Button = findViewById(R.id.replayBtn)
                    winnerDisplay.text = winner
                    winnerDisplay.visibility = View.VISIBLE
                    replayBtn.visibility = View.VISIBLE
                }
            }
        }
    }
}