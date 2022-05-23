package com.app.sensorApp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import sensorApp.R

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null
    private var accelerometer: Sensor? = null
    private var termometer: Sensor? = null

    private lateinit var text: TextView
    private lateinit var square: TextView
    private lateinit var temperature: TextView
    private lateinit var batteryTemperature: TextView
    private lateinit var pb: CircularProgressBar

    // initialize a new broadcast receiver instance
    private val receiver: BroadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            // get battery temperature programmatically from intent
            // battery temperature in tenths of a degree centigrade
            intent?.apply {
                val temp = getIntExtra(
                    BatteryManager.EXTRA_TEMPERATURE,0
                )/10F

                // show the battery temperate in text view
                batteryTemperature = findViewById(R.id.tv_battery_temp)
                batteryTemperature.text = "Battery Temperature: $temp${0x00B0.toChar()}C"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Sensor App"
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        square = findViewById(R.id.tv_acc)
        text = findViewById(R.id.tv_text)
        temperature = findViewById(R.id.tv_temperature)
        pb = findViewById(R.id.circularProgressBar)

        setUpSensorStuff()
        // initialize a new intent filter instance
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        // register the broadcast receiver
        registerReceiver(receiver,filter)
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            termometer = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        } else {
            temperature.text = "Termometer not available!"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = event.values[0]

            text.text = "Sensor: ${light1}\n${brightness(light1)}"
            pb.setProgressWithAnimation(light1*10)
        }
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val sides = event.values[0]
            val upDown = event.values[1]

            square.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.RED
            square.setBackgroundColor(color)

            square.text = "up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}"
        }

        if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val temp = event.values[0]
            temperature.text = "Temperature: ${temp} °C"
        }
    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Pitch black"
            in 1..10 -> "Dark"
            in 11..50 -> "Grey"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Incredibly bright"
            else -> "This light will blind you"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, termometer, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}