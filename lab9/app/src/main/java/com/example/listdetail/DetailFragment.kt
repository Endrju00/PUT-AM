package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class DetailFragment : Fragment(R.layout.fragment_detail) {
    companion object {
        fun newInstance(route: String, description: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("route", route)
            args.putString("description", description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView = view.findViewById<TextView>(R.id.detail_textview)
        val textView2 = view.findViewById<TextView>(R.id.detail_textview2)
        textView.text = this.arguments?.getString("route")
        textView2.text = this.arguments?.getString("description")
    }
}