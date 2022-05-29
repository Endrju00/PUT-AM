package com.example.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var rotateOpen: Animation
    private lateinit var rotateClose: Animation
    private lateinit var fromBottom: Animation
    private lateinit var toBottom: Animation
    private lateinit var mainBtn: FloatingActionButton
    private lateinit var statBtn: FloatingActionButton
    private lateinit var timerBtn: FloatingActionButton
    private var clicked = false

    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var fragmentContainer: FragmentContainerView

    companion object {
        fun newInstance(route: String, description: String, imageId: Int): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("route", route)
            args.putString("description", description)
            args.putInt("imageId", imageId)
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
        val imageView = view.findViewById<ImageView>(R.id.detailimage)
        textView3 = view.findViewById(R.id.detail_textview3)
        textView4 = view.findViewById(R.id.detail_textview4)
        fragmentContainer = view.findViewById(R.id.fragment_container3)

        textView.text = this.arguments?.getString("route")
        textView2.text = this.arguments?.getString("description")
        val imageId = this.arguments?.getInt("imageId")
        val image = if (imageId == 1) R.drawable.route else R.drawable.route2
        imageView.setImageResource(image)

        val sharedTime = requireActivity().getSharedPreferences("com.example.listdetail.shared",0)
        "Best time: ${sharedTime.getString(textView.text.toString(), "None")}".also { textView3.text = it }
        "Last time: ${sharedTime.getString("${textView.text} last", "None")}".also { textView4.text = it }

        if (savedInstanceState == null) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<StopWatchFragment>(
                    R.id.fragment_container3,
                    "SS",
                    Bundle().apply { putString("route", textView.text.toString()) }
                )
            }
        } else {
            textView3.visibility = savedInstanceState.getInt("statsVisibility")
            textView4.visibility = savedInstanceState.getInt("statsVisibility")
            fragmentContainer.visibility = savedInstanceState.getInt("timerVisibility")
        }

        rotateOpen = AnimationUtils.loadAnimation(view.context, R.anim.rotate_open_anim)
        rotateClose = AnimationUtils.loadAnimation(view.context, R.anim.rotate_close_anim)
        fromBottom = AnimationUtils.loadAnimation(view.context, R.anim.from_bottom_anim)
        toBottom = AnimationUtils.loadAnimation(view.context, R.anim.to_bottom)

        mainBtn = view.findViewById(R.id.main_btn)
        statBtn = view.findViewById(R.id.stat_btn)
        timerBtn = view.findViewById(R.id.timer_btm)

        mainBtn.setOnClickListener {
            onMainButtonClicked()
        }

        statBtn.setOnClickListener {
            textView3.visibility = if (textView3.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            textView4.visibility = if (textView4.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }

        timerBtn.setOnClickListener {
            fragmentContainer.visibility = if (fragmentContainer.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save timer data before screen rotate
        outState.putInt("timerVisibility", fragmentContainer.visibility)
        outState.putInt("statsVisibility", textView3.visibility)
    }

    private fun onMainButtonClicked() {
        setVisibility()
        setAnimation()
        setClickable()
        clicked = !clicked
    }

    private fun setAnimation() {
        if (clicked) {
            statBtn.visibility = View.INVISIBLE
            timerBtn.visibility = View.INVISIBLE
        } else {
            statBtn.visibility = View.VISIBLE
            timerBtn.visibility = View.VISIBLE
        }
    }

    private fun setVisibility() {
        if (clicked) {
            statBtn.startAnimation(toBottom)
            timerBtn.startAnimation(toBottom)
            mainBtn.startAnimation(rotateClose)
        } else {
            statBtn.startAnimation(fromBottom)
            timerBtn.startAnimation(fromBottom)
            mainBtn.startAnimation(rotateOpen)
        }
    }

    private fun setClickable() {
        if (clicked) {
            statBtn.isClickable = false
            timerBtn.isClickable = false
        } else {
            statBtn.isClickable = true
            timerBtn.isClickable = true
        }
    }
}