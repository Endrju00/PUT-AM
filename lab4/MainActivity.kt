package com.example.randomgame

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

var FLAG = false
var RAND = (0..20).random()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getRecord()
    }

    fun goTopScore(view: View) {
        val intent = Intent(this, RankingActivity::class.java)
        startActivity(intent)
    }

    private fun setRecord(points: Int){
        val pointsText = findViewById<TextView>(R.id.points)
        val pointsNum = pointsText.text.toString().toInt() + points
        findViewById<TextView>(R.id.points).apply { text = pointsNum.toString() }

        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val edit = sharedScore.edit()

        edit.putInt("score", pointsNum)
        edit.apply()

        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.writableDatabase
        val sharedNickname = this.getSharedPreferences("com.example.myapplication.shared",0)
        val nickname = sharedNickname.getString("nickname", "Test")

        // Create a new map of values, where column names are the keys
        val password = "hacker_entrance"

        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME, nickname)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, password)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE, pointsNum)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    }

    fun getRecord() {
//        val sharedScore = this.getSharedPreferences("com.example.myapplication.shared",0)
        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.readableDatabase

        val sharedNickname = this.getSharedPreferences("com.example.myapplication.shared", 0)
        val nickname = sharedNickname.getString("nickname", "Test")

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE
        )
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE} DESC"
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME} = ?"
        val selectionArgs = arrayOf(nickname)

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder              // The sort order
        )

        with(cursor) {
            if (moveToNext()) {
                val points = getInt(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE))
                findViewById<TextView>(R.id.points).apply { text = points.toString() }
            } else {
                findViewById<TextView>(R.id.points).apply { text = 0.toString() }
            }
        }
        cursor.close()
    }

    fun newGame(view: View) {
        findViewById<TextView>(R.id.guesses).apply { text= 0.toString() }
        RAND = (0..20).random()
    }

    /** Called when the user taps the Send button */
    fun sendMessage(view: View) {
        if (FLAG) {
            findViewById<TextView>(R.id.guesses).apply { text= 0.toString() }
            RAND = (0..20).random()
        }
        FLAG = false

        // VALIDATE INPUT
        val editText = findViewById<EditText>(R.id.editText)
        val input = editText.text.toString()
        val numeric = input.matches("-?\\d+(\\.\\d+)?".toRegex())
        var number = 0

        if (numeric) {
            number = input.toInt()
        }

        if (number > 20 || number < 0 || !numeric) {
            Toast.makeText(applicationContext, "Enter a number from 0 to 20.", Toast.LENGTH_SHORT).show()
        } else {
            // GUESSES
            val guessesText = findViewById<TextView>(R.id.guesses)
            val guesses = guessesText.text.toString().toInt() + 1
            findViewById<TextView>(R.id.guesses).apply { text=guesses.toString() }

            if (number == RAND) {
                // CALCULATE POINTS
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
                setRecord(points)

                // WINNER
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle(title)
                builder.setMessage("Congratulations! You won :) click GUESS to play again.")
                builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}
                val dialog: AlertDialog = builder.create()
                dialog.show()

                FLAG = true
            } else {
                // HINTS
                if (number < RAND) {
                    Toast.makeText(applicationContext, "The number is greater than yours", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "The number is less than yours", Toast.LENGTH_SHORT).show()
                }

                val guessesText = findViewById<TextView>(R.id.guesses)
                if (guessesText.text.toString().toInt() >= 10) {
                    // LOOSER
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle(title)
                    builder.setMessage("You lost :( click GUESS to play again.")

                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    FLAG = true
                }
            }
        }
    }
}