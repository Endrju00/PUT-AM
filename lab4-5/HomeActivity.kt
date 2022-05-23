package com.example.randomgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val thread = Thread(){
            run{
                Thread.sleep(3000)
            }
            runOnUiThread(){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }
}