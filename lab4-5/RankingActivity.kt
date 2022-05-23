package com.example.randomgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        getTopScores()
    }

    fun goBack(view: View) {
        finish()
    }

    fun getTopScores() {
        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME, FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE)
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE} DESC"

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder              // The sort order
        )

        var counter = 1
        with(cursor) {
            while (moveToNext() && counter != 11) {
                val nickname = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME))
                val topScore = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE))
                val row = "$counter. $nickname: $topScore points"
                when (counter) {
                    1 -> {
                        findViewById<TextView>(R.id.top1).apply { text = row }
                    }
                    2 -> {
                        findViewById<TextView>(R.id.top2).apply { text = row }
                    }
                    3 -> {
                        findViewById<TextView>(R.id.top3).apply { text = row }
                    }
                    4 -> {
                        findViewById<TextView>(R.id.top4).apply { text = row }
                    }
                    5 -> {
                        findViewById<TextView>(R.id.top5).apply { text = row }
                    }
                    6 -> {
                        findViewById<TextView>(R.id.top6).apply { text = row }
                    }
                    7 -> {
                        findViewById<TextView>(R.id.top7).apply { text = row }
                    }
                    8 -> {
                        findViewById<TextView>(R.id.top8).apply { text = row }
                    }
                    9 -> {
                        findViewById<TextView>(R.id.top9).apply { text = row }
                    }
                    10 -> {
                        findViewById<TextView>(R.id.top10).apply { text = row }
                    }
                }
                counter += 1
            }
        }
        cursor.close()
    }
}