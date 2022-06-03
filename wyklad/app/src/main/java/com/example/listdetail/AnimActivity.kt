package com.example.listdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AnimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)

        val thread = Thread {
            run{
                val fm = supportFragmentManager
                var fragment = fm.findFragmentById(R.id.fragment_container)
                if (fragment == null){
                    fragment = ShoeFragment.newInstance()
                    fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
                }
                Thread.sleep(4000)
            }
            runOnUiThread {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }
}

