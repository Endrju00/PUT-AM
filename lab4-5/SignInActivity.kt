package com.example.randomgame

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun goLogIn(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signIn(view: View) {
        var dbHelper = FeedReaderDbHelper(this)
        var db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME, FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD)
        val nickname = findViewById<EditText>(R.id.nickname).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME} = ?"
        val selectionArgs = arrayOf(nickname)
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME} DESC"

        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder              // The sort order
        )

        // Check if user exists
        var found = false
        with(cursor) {
            if (moveToNext()) {
                found = true
            }
        }
        cursor.close()

        if (found) {
            Toast.makeText(applicationContext, "User with this nickname already exists.", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper = FeedReaderDbHelper(this)
            db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME, nickname)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, password)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_TOPSCORE, 0)
            }

            // Insert the new row, returning the primary key value of the new row
            val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
            Toast.makeText(applicationContext, "Account created", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}