package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment(R.layout.fragment_list) {

    companion object {
        fun newInstance(difficulty: String): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putString("difficulty", difficulty)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val difficulty = this.arguments?.getString("difficulty")
        var routes = DB(requireContext()).allRouteNames

        when (difficulty) {
            "easy" -> routes = getEasyRoutes(routes)
            "difficult" -> routes = getDifficultRoutes(routes)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerAdapter(routes, requireContext())
    }

    private fun getDifficultRoutes(routes: Array<String>): Array<String> {
        val list = mutableListOf<String>()
        routes.forEachIndexed{ i, v -> if (i%2==0) list.add(v)}
        return list.toTypedArray()
    }

    private fun getEasyRoutes(routes: Array<String>): Array<String> {
        val list = mutableListOf<String>()
        routes.forEachIndexed{ i, v -> if (i%2==1) list.add(v)}
        return list.toTypedArray()
    }
}