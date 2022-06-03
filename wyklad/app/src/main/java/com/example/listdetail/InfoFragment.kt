package com.example.listdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class InfoFragment : Fragment(R.layout.fragment_info) {
    companion object {
        fun newInstance(temperature: String): InfoFragment {
            val fragment = InfoFragment()
            val args = Bundle()
            args.putString("temperature", temperature)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val switch = view.findViewById<Switch>(R.id.switch1)
        var temperatureTv = view?.findViewById<TextView>(R.id.temperature)

        switch.setOnCheckedChangeListener { _, checked ->
            if (checked)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
        temperatureTv.text = this.arguments?.getString("temperature")
    }
}