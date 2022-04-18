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

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.title = "User's TODOs and posts"

        val todoListView = findViewById<ListView>(R.id.todos_list)
        todoListView.adapter = TodoAdapter(applicationContext)

        val postListView = findViewById<ListView>(R.id.posts_list)
        postListView.adapter = PostAdapter(applicationContext)
        postListView.setOnItemClickListener{ parent, view, position, id ->
            CLICKED_COMMENTS = emptyList()
            val postId = CLICKED_POSTS[position].id
            for (comment in COMMENTS) {
                if (comment.postId == postId) {
                    CLICKED_COMMENTS = CLICKED_COMMENTS + comment
                }
            }
            val intent = Intent(this, CommentActivity::class.java)
            startActivity(intent)
        }
    }

    fun goToUsers(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private class TodoAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        override fun getCount(): Int {
            return CLICKED_TODOS.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_todo, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.todo_name)
            val title = CLICKED_TODOS[position].title
            nameTextView.text = "Title: $title"

            val completedTextView = rowMain.findViewById<TextView>(R.id.todo_completed)
            val completed = CLICKED_TODOS[position].completed.toString()
            completedTextView.text = "Completed: $completed"

            return rowMain
        }
    }

    private class PostAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        override fun getCount(): Int {
            return CLICKED_POSTS.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_post, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.post_text)
            val title = CLICKED_POSTS[position].title
            nameTextView.text = "Title: $title"

            val bodyTextView = rowMain.findViewById<TextView>(R.id.post_body)
            bodyTextView.text = CLICKED_POSTS[position].body

            return rowMain
        }
    }
}