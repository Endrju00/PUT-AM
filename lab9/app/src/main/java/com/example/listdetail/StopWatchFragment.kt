package com.example.listdetail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt


class StopWatchFragment : Fragment(R.layout.fragment_stop_watch) {
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private lateinit var timeTv: TextView
    private lateinit var startStopButton: MaterialButton
    private lateinit var resetButton: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startStopButton = view.findViewById(R.id.startStopButton)
        resetButton = view.findViewById(R.id.resetButton)
        val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)
                timeTv = view.findViewById(R.id.timeTV)
                timeTv.text = getTimeStringFromDouble(time)
            }
        }

        startStopButton.setOnClickListener { startStopTimer() }
        resetButton.setOnClickListener { resetTimer() }

        serviceIntent = Intent(requireActivity().applicationContext, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        timeTv.text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
        requireActivity().startService(serviceIntent)
        startStopButton.text = "Stop"
        startStopButton.icon = requireActivity().getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        startStopButton.text = "Start"
        startStopButton.icon = requireActivity().getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
}