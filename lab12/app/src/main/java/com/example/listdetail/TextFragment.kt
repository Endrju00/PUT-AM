package com.example.listdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TextFragment: Fragment(R.layout.fragment_shoe) {
    private lateinit var mSceneView: View
    private lateinit var mTextView: View
    private lateinit var mShoeView: View


    companion object {
        fun newInstance(): TextFragment{
            return TextFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_text, container, false)
        mSceneView = view
        mTextView = view.findViewById(R.id.textView)
        mTextView.alpha = 0f
        mShoeView = view.findViewById(R.id.shoe2)

        startAnimation(mSceneView, 900)

        return view
    }

    private fun startAnimation(view: View, duration: Long){
        var  shoeScaleAnimator = ObjectAnimator.ofFloat(mShoeView, "rotation", -70f, 0f)
        shoeScaleAnimator.duration = duration
        var shoeYAnimator = ObjectAnimator.ofFloat(mShoeView, "translationY", -750f, 150f)
        shoeYAnimator.duration = duration
        var textColorAnimator = ObjectAnimator.ofFloat(mTextView, "alpha", 0f, 1f)
        textColorAnimator.duration = duration / 2
        var animatorSet = AnimatorSet()
        animatorSet.play(shoeScaleAnimator).with(shoeYAnimator).before(textColorAnimator)
        animatorSet.start()
    }
}