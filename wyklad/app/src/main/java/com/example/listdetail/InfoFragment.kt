package com.example.listdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class InfoFragment : Fragment(R.layout.fragment_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val switch = view.findViewById<Switch>(R.id.switch1)
        switch.setOnCheckedChangeListener { _, checked ->
            if (checked)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }
}