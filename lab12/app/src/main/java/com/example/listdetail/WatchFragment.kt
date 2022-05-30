package com.example.listdetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class WatchFragment: Fragment(R.layout.fragment_watch) {
    private lateinit var mSceneView: View
    private lateinit var mWatchView: View
    private lateinit var mArrowView: View


    companion object {
        fun newInstance(): WatchFragment{
            return WatchFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_watch, container, false)
        mSceneView = view
        mWatchView = view.findViewById(R.id.watch)
        mArrowView = view.findViewById(R.id.arrow)

        startAnimation(mSceneView, 3000)

        return view
    }

    private fun startAnimation(view: View, duration: Long){
        var  arrowRotateAnim = ObjectAnimator.ofFloat(mArrowView, "rotation", -50f, 1038f)
        arrowRotateAnim.duration = duration
        var animatorSet = AnimatorSet()
        animatorSet.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                // change activity
                val intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
        })
        animatorSet.play(arrowRotateAnim)
        animatorSet.start()
    }
}