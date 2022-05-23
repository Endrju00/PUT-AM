package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment


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

        )
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, list)
        lv.adapter = adapter
    }
}