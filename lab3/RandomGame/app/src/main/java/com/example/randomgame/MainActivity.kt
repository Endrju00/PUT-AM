package com.example.randomgame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "com.example.randomgame.MESSAGE"
var FLAG = false
var RAND = (0..20).random()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Send button */
    fun sendMessage(view: View) {
        if (FLAG) {
            findViewById<TextView>(R.id.guesses).apply { text= 0.toString() }
            findViewById<TextView>(R.id.points).apply { text=0.toString() }
            findViewById<TextView>(R.id.win).apply { visibility=View.INVISIBLE }
            RAND = (0..20).random()
        }
        FLAG = false

        val editText = findViewById<EditText>(R.id.editText)
        val input = editText.text.toString()
        val numeric = input.matches("-?\\d+(\\.\\d+)?".toRegex())
        var number = 0

        if (numeric) {
            number = input.toInt()
        }

        if (number > 20 || number < 0) {
            findViewById<TextView>(R.id.win).apply {
                text = "Enter a number from 0 to 20!"; visibility =
                View.VISIBLE
            }
        } else {

            val guessesText = findViewById<TextView>(R.id.guesses)
            val guesses = guessesText.text.toString().toInt() + 1
            findViewById<TextView>(R.id.guesses).apply { text=guesses.toString() }

            if (number == RAND) {
                var points = 0
                val guessesText = findViewById<TextView>(R.id.guesses)
                when (guessesText.text.toString().toInt()) {
                    1 -> {
                        points = 5
                    }
                    in 2..4 -> {
                        points = 3
                    }
                    in 5..6 -> {
                        points = 2
                    }
                    in 7..10 -> {
                        points = 1
                    }
                }
                val pointsText = findViewById<TextView>(R.id.points)
                val pointsNum = pointsText.text.toString().toInt() + points
                findViewById<TextView>(R.id.points).apply { text = pointsNum.toString() }
                findViewById<TextView>(R.id.win).apply {
                    text = "Congratulations! You won :) click GUESS to play again."; visibility =
                    View.VISIBLE
                }
                FLAG = true
            } else {

                if (number < RAND) {
                    findViewById<TextView>(R.id.win).apply {
                        text = "The number is greater than yours"; visibility = View.VISIBLE
                    }
                } else {
                    findViewById<TextView>(R.id.win).apply {
                        text = "The number is less than yours"; visibility = View.VISIBLE
                    }
                }

                val guessesText = findViewById<TextView>(R.id.guesses)
                if (guessesText.text.toString().toInt() > 10) {
                    findViewById<TextView>(R.id.win).apply {
                        text = "You lost :( click GUESS to play again."; visibility = View.VISIBLE
                    }
                    FLAG = true
                }
            }
        }
    }
}