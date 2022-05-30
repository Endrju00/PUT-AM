package com.example.listdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AnimActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        var fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null){
            // here you can change animation
            fragment = ShoeFragment.newInstance()
//            fragment = TextFragment.newInstance()
//            fragment = WatchFragment.newInstance()
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}