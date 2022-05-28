package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit


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
        val textView3 = view.findViewById<TextView>(R.id.detail_textview3)
        val textView4 = view.findViewById<TextView>(R.id.detail_textview4)
        textView.text = this.arguments?.getString("route")
        textView2.text = this.arguments?.getString("description")

        val sharedTime = requireActivity().getSharedPreferences("com.example.listdetail.shared",0)
        textView3.text = "Best time: ${sharedTime.getString(textView.text.toString(), "None")}"
        textView4.text = "Last time: ${sharedTime.getString("${textView.text.toString()} last", "None")}"

        if (savedInstanceState == null) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<StopWatchFragment>(
                    R.id.fragment_container3,
                    "SS",
                    Bundle().apply { putString("route", textView.text.toString()) }
                )
            }
        }

    }
}