package com.example.listdetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class ShoeFragment: Fragment(R.layout.fragment_shoe) {
    private lateinit var mSceneView: View
    private lateinit var mLoadView: View
    private lateinit var mShoeView: View
    private lateinit var mSkyView: View

    companion object {
        fun newInstance(): ShoeFragment{
            return ShoeFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_shoe, container, false)
        mSceneView = view
        mLoadView = view.findViewById(R.id.loader)
        mShoeView = view.findViewById(R.id.shoe)
        mSkyView = view.findViewById(R.id.sky)

        startAnimation(mSceneView, 1500)

        return view
    }

    private fun startAnimation(view: View, duration: Long){
        val heightAnimator: ObjectAnimator = ObjectAnimator.ofFloat(mLoadView, "scaleX", 1f, 20f)
        heightAnimator.duration = duration*2
        val  shoeScaleAnimator = ObjectAnimator.ofFloat(mShoeView, "rotation", 50f, 0f)
        shoeScaleAnimator.duration = duration
        val shoeXAnimator = ObjectAnimator.ofFloat(mShoeView, "translationX", 300f, 0f)
        shoeXAnimator.duration = duration
        val shoeYAnimator = ObjectAnimator.ofFloat(mShoeView, "translationY", -150f, 0f)
        shoeYAnimator.duration = duration
        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator).with(shoeScaleAnimator).with(shoeXAnimator).with(shoeYAnimator)
        animatorSet.start()
    }
}