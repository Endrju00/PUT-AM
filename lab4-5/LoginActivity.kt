package com.example.randomgame

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goRegister(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goTopScore(view: View) {
        val intent = Intent(this, RankingActivity::class.java)
        startActivity(intent)
    }

    fun logIn(view: View) {
        val dbHelper = FeedReaderDbHelper(this)
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME, FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD)

        // Filter results WHERE "nickname" = nickname
        val nickname = findViewById<EditText>(R.id.nickname_login).text.toString()
        val password = findViewById<EditText>(R.id.password_login).text.toString()
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_NICKNAME} = ?"
        val selectionArgs = arrayOf(nickname)

        // How you want the results sorted in the resulting Cursor
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

        val intent = Intent(this, MainActivity::class.java)
        var notFound = true
        with(cursor) {
            while (moveToNext()) {
                val passwordDB = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD))
                if (password.equals(passwordDB)) {
                    startActivity(intent)
                    notFound = false
                    break
                }
            }
        }
        if (notFound) {
            Toast.makeText(applicationContext, "Wrong password or nickname.", Toast.LENGTH_SHORT).show()
        } else {
            val shared = this.getSharedPreferences("com.example.myapplication.shared", 0)
            val edit = shared.edit()
            edit.putString("nickname", nickname)
            edit.apply()
        }
        cursor.close()
        if (!notFound) {
            finish()
        }
    }
}