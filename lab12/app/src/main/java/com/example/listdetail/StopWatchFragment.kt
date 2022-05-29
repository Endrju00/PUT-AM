package com.example.listdetail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.lang.Float.POSITIVE_INFINITY
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class StopWatchFragment : Fragment(R.layout.fragment_stop_watch) {
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private lateinit var timeTv: TextView
    private lateinit var startStopButton: MaterialButton
    private lateinit var resetButton: MaterialButton
    private lateinit var saveButton: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startStopButton = view.findViewById(R.id.startStopButton)
        resetButton = view.findViewById(R.id.resetButton)
        saveButton = view.findViewById(R.id.saveButton)
        timeTv = view.findViewById(R.id.timeTV)

        // If screen was rotated the instance is recreated so we need to restore values
        if (savedInstanceState != null) {
            startStopButton.text = savedInstanceState.getString("startStopButtonText")
            startStopButton.icon = requireActivity().getDrawable(savedInstanceState.getInt("startStopButtonIconId"))
            timerStarted = savedInstanceState.getBoolean("timerStarted")
            time = savedInstanceState.getDouble("time")
            timeTv.text = getTimeStringFromDouble(time)
        }

        val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)
                timeTv.text = getTimeStringFromDouble(time)
            }
        }

        startStopButton.setOnClickListener { startStopTimer() }
        resetButton.setOnClickListener { resetTimer() }
        saveButton.setOnClickListener {
            stopTimer()
            val route = this.arguments?.getString("route")
            val sharedTime = requireActivity().getSharedPreferences("com.example.listdetail.shared",0)
            val record = sharedTime.getFloat("$route time", POSITIVE_INFINITY)
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())

            val edit = sharedTime.edit()
            if (record > time.toFloat()) {
                edit.putString(route, timeTv.text.toString() + " at " + currentDate)
                edit.putFloat("$route time", time.toFloat())
                edit.apply()
                Toast.makeText(view.context, "This is the best time ever for $route! Time has been saved.", Toast.LENGTH_LONG).show()
            }
            edit.putString("$route last", timeTv.text.toString() + " at " + currentDate)
            edit.apply()
            Toast.makeText(view.context, "The $route has been completed! Time has been saved.", Toast.LENGTH_LONG).show()
        }

        serviceIntent = Intent(requireActivity().applicationContext, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    private fun resetTimer() {
        time = 0.0
        timeTv.text = getTimeStringFromDouble(time)
        if (timerStarted)
            stopTimer()
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
        startStopButton.text = "  Stop"
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save timer data before screen rotate
        if (timerStarted)
            outState.putInt("startStopButtonIconId", R.drawable.ic_baseline_pause_24)
        else
            outState.putInt("startStopButtonIconId", R.drawable.ic_baseline_play_arrow_24)

        outState.putString("startStopButtonText", startStopButton.text.toString())
        outState.putBoolean("timerStarted", timerStarted)
        outState.putDouble("time", time)
    }
}