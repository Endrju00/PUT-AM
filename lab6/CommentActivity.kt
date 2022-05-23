package com.example.jsonapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.title = "Post's comments"

        val commentListView = findViewById<ListView>(R.id.comments_list)
        commentListView.adapter = CommentAdapter(applicationContext)

    }

    fun goToUsers(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToUserData(view: View){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        finish()
    }

    private class CommentAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        override fun getCount(): Int {
            return CLICKED_COMMENTS.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_comment, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.comment_name)
            val title = CLICKED_COMMENTS[position].name
            nameTextView.text = "Name: $title"

            val emailTextView = rowMain.findViewById<TextView>(R.id.comment_email)
            val email = CLICKED_COMMENTS[position].email
            emailTextView.text = "Email:: $email"

            val bodyTextView = rowMain.findViewById<TextView>(R.id.comment_body)
            bodyTextView.text = CLICKED_COMMENTS[position].body

            return rowMain
        }
    }
}