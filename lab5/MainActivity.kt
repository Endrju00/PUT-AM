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
import android.widget.Toast
import com.example.jsonapp.models.Comment
import com.example.jsonapp.models.Post
import com.example.jsonapp.models.Todo
import com.example.jsonapp.models.User
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

var USERS: List<User> = emptyList()
var TODOS: List<Todo> = emptyList()
var POSTS: List<Post> = emptyList()
var COMMENTS: List<Comment> = emptyList()
var CLICKED_POSTS: List<Post> = emptyList()
var CLICKED_TODOS: List<Todo> = emptyList()
var CLICKED_COMMENTS: List<Comment> = emptyList()

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Users"

        val thread = Thread() {
            run {
                getUsers()
                while (USERS.isEmpty()) {
                    Thread.sleep(1)
                }
                getTodos()
                while (TODOS.isEmpty()) {
                    Thread.sleep(1)
                }
                getPosts()
                while (POSTS.isEmpty()) {
                    Thread.sleep(1)
                }
                getComments()
                while (COMMENTS.isEmpty()) {
                    Thread.sleep(1)
                }
            }
            runOnUiThread() {
                countTodos()
                countPosts()
                val listView = findViewById<ListView>(R.id.main_listview)
                listView.adapter = CustomAdapter(applicationContext)
                listView.setOnItemClickListener{ parent, view, position, id ->
                    CLICKED_POSTS = emptyList()
                    CLICKED_TODOS = emptyList()
                    val userId = USERS[position].id
                    for (post in POSTS){
                        if (post.userId == userId) {
                            CLICKED_POSTS = CLICKED_POSTS + post
                        }
                    }

                    for (todo in TODOS){
                        if (todo.userId == userId) {
                            CLICKED_TODOS = CLICKED_TODOS + todo
                        }
                    }

                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        thread.start()
    }

    private class CustomAdapter(context: Context): BaseAdapter() {

        private val mContext: Context = context

        override fun getCount(): Int {
            return USERS.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)

            val nameTextView = rowMain.findViewById<TextView>(R.id.name_textView)
            nameTextView.text = USERS[position].name

            val emailTextView = rowMain.findViewById<TextView>(R.id.email_textView)
            emailTextView.text = USERS[position].email

            val tasksTextView = rowMain.findViewById<TextView>(R.id.tasks_textView)
            val todoCounter = USERS[position].todos
            tasksTextView.text = "Todos: $todoCounter"

            val postTextView = rowMain.findViewById<TextView>(R.id.posts_textView2)
            val postCounter = USERS[position].posts
            postTextView.text = "Posts: $postCounter"

            return rowMain
        }
    }

    fun getUsers() {
        val url = "https://jsonplaceholder.typicode.com/users"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                USERS = gson.fromJson(body, object : TypeToken<List<User>>() {}.type)
            }
        })
    }

    fun getTodos() {
        val url = "https://jsonplaceholder.typicode.com/todos"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                TODOS = gson.fromJson(body, object : TypeToken<List<Todo>>() {}.type)
            }
        })
    }

    fun getPosts() {
        val url = "https://jsonplaceholder.typicode.com/posts"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                POSTS = gson.fromJson(body, object : TypeToken<List<Post>>() {}.type)
            }
        })
    }

    fun getComments() {
        val url = "https://jsonplaceholder.typicode.com/comments"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                COMMENTS = gson.fromJson(body, object : TypeToken<List<Comment>>() {}.type)
            }
        })
    }

    fun countTodos() {
        for (todo in TODOS) {
            var i = 0
            for (user in USERS) {
                if (user.id == todo.userId) {
                    USERS[i].todos += 1
                    break
                }
                i += 1
            }
        }
    }

    fun countPosts() {
        for (post in POSTS) {
            println(post.userId)
            var i = 0
            for (user in USERS) {
                if (user.id == post.userId) {
                    USERS[i].posts += 1
                    val s = USERS[i].posts
                    break
                }
                i += 1
            }
        }
    }
}