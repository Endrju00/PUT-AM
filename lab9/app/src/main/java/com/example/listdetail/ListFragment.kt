package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

import android.util.Log
import android.widget.TextView

class ListFragment : Fragment(R.layout.fragment_list) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lv = view.findViewById<ListView>(R.id.listview)
        val routes = arrayOf(
            "Droga 1", "Droga 2", "Droga 3", "Droga 4", "Droga 5",
            "Droga 6", "Droga 7", "Droga 8", "Droga 9", "Droga 10",
            "Droga 11", "Droga 12", "Droga 13", "Droga 14", "Droga 15",
            "Droga 16", "Droga 17", "Droga 18", "Droga 19", "Droga 20",
        )
        val descriptions = mapOf(
            "Droga 1" to "Opis drogi 1", "Droga 2" to "Opis drogi 2", "Droga 3" to "Opis drogi 3",
            "Droga 4" to "Opis drogi 4", "Droga 5" to "Opis drogi 5", "Droga 6" to "Opis drogi 6",
            "Droga 7" to "Opis drogi 7", "Droga 8" to "Opis drogi 8", "Droga 9" to "Opis drogi 9",
            "Droga 10" to "Opis drogi 10", "Droga 11" to "Opis drogi 11", "Droga 12" to "Opis drogi 12",
            "Droga 13" to "Opis drogi 13", "Droga 14" to "Opis drogi 14", "Droga 15" to "Opis drogi 15",
            "Droga 16" to "Opis drogi 16", "Droga 17" to "Opis drogi 17", "Droga 18" to "Opis drogi 18",
            "Droga 19" to "Opis drogi 19", "Droga 20" to "Opis drogi 20",
        )


        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, routes)
        lv.adapter = adapter

        lv.setOnItemClickListener { parent, view, position, id ->
            val route = adapter.getItem(position)
            val description = descriptions[route]
            replaceFragment(DetailFragment.newInstance(route.toString(), description.toString()))
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val smallestWidth = resources.configuration.smallestScreenWidthDp
        val fragmentTransaction = parentFragmentManager.beginTransaction()

        if (smallestWidth < 720)
            fragmentTransaction.replace(R.id.fragment_container, fragment)
        else
            fragmentTransaction.replace(R.id.fragment_container2, fragment)

        fragmentTransaction.commit()
    }
}