package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.hfad.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    //Add lines for keys, used with Bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Getting a link to a stopwatch
        stopwatch = findViewById(R.id.stopwatch)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                /*
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()

                 */
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }

        //The start button starts stopwatch, if it was not running
        //val startButton = findViewById<Button>(R.id.start_button)
        binding.startButton.setOnClickListener {
            //startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }


        //The pause button stops the stopwatch, if it was running
        //val pauseButton = findViewById<Button>(R.id.pause_button)

        //pauseButton.setOnClickListener {
        binding.pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }


        //The reset button resets offset and base time
        /*
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {

         */
        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    //override fun onStop() {
    //  super.onStop()
    override fun onPause() {
        super.onPause()
        if (running) {
            saveOffset()
            binding.stopwatch.stop()
        }
    }

    //override fun onStart() {
    //  super.onStart()
    //}

    //override fun onRestart() {
    // super.onRestart()

    override fun onResume() {
        super.onResume()
        if (running) {
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, binding.stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    //override fun onStop() {
    //  super.onStop()

    //Updates the time stopwatch.base
    private fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    //The Saves offset
    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }
}