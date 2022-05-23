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
        val list = arrayOf(
            "Droga 1",
            "Droga 2",
            "Droga 3",
            "Droga 4",
            "Droga 5",
            "Droga 6",
            "Droga 7",
            "Droga 8",
            "Droga 9",
            "Droga 10",
            "Droga 11",
            "Droga 12",
            "Droga 13",
            "Droga 14",
            "Droga 15",
            "Droga 16",
            "Droga 17",
            "Droga 18",
            "Droga 19",
            "Droga 20",
            )

        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list)
        lv.adapter = adapter

        lv.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position)
            replaceFragment(DetailFragment.newInstance(element.toString()))
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val smallestWidth = resources.configuration.smallestScreenWidthDp
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (smallestWidth < 720)
            fragmentTransaction.replace(R.id.fragment_container, fragment)
        else
            fragmentTransaction.replace(R.id.fragment_container2, fragment)

        fragmentTransaction.commit()
    }
}