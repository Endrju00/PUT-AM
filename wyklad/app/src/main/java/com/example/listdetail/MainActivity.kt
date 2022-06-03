package com.example.listdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener

class MainActivity : AppCompatActivity(R.layout.activity_main), GestureDetector.OnGestureListener {
    private val easyRoutes = ListFragment.newInstance("easy")
    private val difficultRoutes = ListFragment.newInstance("difficult")
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var gestureDetector: GestureDetector
    private var x2: Float = 0.0f
    private var x1: Float = 0.0f
    private var tabId = 0
    private val client = OkHttpClient()
    private var loading = "Loading..."

    companion object {
        const val MIN_DISTANCE = 150
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = Thread {
            run {
                val url = "https://api.openweathermap.org/data/2.5/weather?q=poznan,pl&appid=2656690346203e6706d0b586863add0d"
                val request = Request.Builder()
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val jsonObject = JSONTokener(response.body()?.string()).nextValue() as JSONObject
                val main = jsonObject.getJSONObject("main")
                val temperature = (main.getString("temp").toFloat().toInt() - 273).toString() + "°C Poznań"
                loading = temperature
                replaceFragment(InfoFragment.newInstance(temperature))
            }
            runOnUiThread {
                if (savedInstanceState == null) {
                    replaceFragment(InfoFragment.newInstance(loading))
                }
                bottomNavigation = findViewById(R.id.bottom_navigation)
                bottomNavigation.setOnNavigationItemSelectedListener {
                    when(it.itemId) {
                        R.id.ic_home -> {
                            replaceFragment(InfoFragment.newInstance(loading))
                            tabId = 0
                        }
                        R.id.ic_easy -> {
                            replaceFragment(easyRoutes)
                            tabId = 1
                        }
                        R.id.ic_difficult -> {
                            replaceFragment(difficultRoutes)
                            tabId = 2
                        }
                    }
                    true
                }

                gestureDetector = GestureDetector(this, this)
            }
        }
        thread.start()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event?.action) {
            0 -> {
                x1 = event.x
            }
            1 -> {
                x2 = event.x

                if (kotlin.math.abs(x2 - x1) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        when (tabId) {
                            0 -> {
                                replaceFragment(easyRoutes)
                                tabId = 1
                            }
                            1 -> {
                                replaceFragment(difficultRoutes)
                                tabId = 2
                            }
                            2 -> {
                                replaceFragment(InfoFragment.newInstance(loading))
                                tabId = 0
                            }
                        }
                    } else {
                        when (tabId) {
                            0 -> {
                                replaceFragment(difficultRoutes)
                                tabId = 2
                            }
                            1 -> {
                                replaceFragment(InfoFragment.newInstance(loading))
                                tabId = 0
                            }
                            2 -> {
                                replaceFragment(easyRoutes)
                                tabId = 1
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {  return false }

    override fun onShowPress(p0: MotionEvent?) {}

    override fun onSingleTapUp(p0: MotionEvent?): Boolean { return false }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean { return false }

    override fun onLongPress(p0: MotionEvent?) {}

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean { return false }
}
