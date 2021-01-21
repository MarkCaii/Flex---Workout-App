package com.example.workoutapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class RestTimerActivity : AppCompatActivity() {
    private lateinit var timer: CountDownTimer
    var running = false
    var paused = false
    var stopped = true
    var repeats = 0
    var count = 0
    lateinit var workoutName: String
    var workout = mutableListOf<String>()
    var countDownInterval: Long = 1000
    var initialTime: Long = 60000
    var timeLeft: Long = 60000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_timer)
        workoutName = intent.extras!!.getString("WORKOUT_NAME").toString()
        for(exercise in MainActivity.workouts[workoutName]!!) {
            repeats += exercise.sets
        }
        initialTime = (intent.extras!!.getString("SECONDS")!!.toLong() + intent.extras!!.getString("MINUTES")!!.toLong()*60)*1000
        timeLeft = initialTime

        val textView = findViewById<TextView>(R.id.time_left)
        if(initialTime%60000/1000 < 10){
            textView.text = (initialTime/60000).toString() + ":0" + (initialTime%60000/1000).toString()
        }else {
            textView.text = (initialTime / 60000).toString() + ":" + (initialTime % 60000 / 1000).toString()
        }

        val startButton = findViewById<FloatingActionButton>(R.id.timer_button)
        val pauseButton = findViewById<FloatingActionButton>(R.id.pause_button)
        val stopButton = findViewById<FloatingActionButton>(R.id.stop_button)

        startButton.setOnClickListener{
            startTimer()
        }
        pauseButton.setOnClickListener{
            pauseTimer()
        }

        stopButton.setOnClickListener {
            stopTimer()
        }
        for(exercise in MainActivity.workouts[workoutName]!!){
            for(i in 0 until exercise.sets){
                workout.add(i, exercise.exercise)
            }
        }
        Log.d("TimerActivity", "Repeats: " + repeats.toString())
        updateQueue()
    }

    private fun updateQueue(){
        Log.d("TimerActivity", "Count: " + count.toString())
        val currentExercise = findViewById<TextView>(R.id.current_exercise)
        val nextExercise = findViewById<TextView>(R.id.next_exercise)
        if(count == repeats-1){
            currentExercise.text = "Current Exercise: " + workout[count]
            nextExercise.text = "This is your last set!"
        }else{
            currentExercise.text = "Current Exercise: " + workout[count]
            nextExercise.text = "Next Exercise: " + workout[count+1]
        }
    }

    private fun stopTimer(){

        val textView = findViewById<TextView>(R.id.time_left)
        if(stopped){
            return
        }
        val progressBar = findViewById<CircularProgressBar>(R.id.timer_progress)
        progressBar.progress = 0F
        stopped = true
        running = false
        paused = false
        timeLeft = initialTime
        if(initialTime%60000/1000 < 10){
            textView.text = (initialTime/60000).toString() + ":0" + (initialTime%60000/1000).toString()
        }else {
            textView.text = (initialTime / 60000).toString() + ":" + (initialTime % 60000 / 1000).toString()
        }
        timer.cancel()
    }

    private fun pauseTimer(){
        val progressBar = findViewById<CircularProgressBar>(R.id.timer_progress)
        progressBar.progressMax = initialTime.toFloat()
        val textView = findViewById<TextView>(R.id.time_left)
        if(paused){
            return
        }
        paused = true
        if(timeLeft%60000/1000 < 10){
            textView.text = (timeLeft/60000).toString() + ":0" + (timeLeft%60000/1000).toString()
        }else {
            textView.text = (timeLeft / 60000).toString() + ":" + (timeLeft % 60000 / 1000).toString()
        }
        timer.cancel()

        timer = object: CountDownTimer(timeLeft, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = (initialTime - timeLeft).toFloat()
                timeLeft = millisUntilFinished
                if(timeLeft%60000/1000 < 10){
                    textView.text = (timeLeft/60000).toString() + ":0" + (timeLeft%60000/1000).toString()
                }else {
                    textView.text = (timeLeft / 60000).toString() + ":" + (timeLeft % 60000 / 1000).toString()
                }
            }
            override fun onFinish(){
                running = false
                count++
                updateQueue()
                progressBar.progress = progressBar.progressMax
                textView.text = "Done!"
            }
        }
        running = false
        stopped = false
    }
    private fun startTimer(){
        val progressBar = findViewById<CircularProgressBar>(R.id.timer_progress)
        progressBar.progressMax = initialTime.toFloat()
        val textView = findViewById<TextView>(R.id.time_left)
        if(running){
            return
        }
        running = true
        if(stopped) {
            timer = object : CountDownTimer(timeLeft, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished
                    progressBar.progress = (initialTime - timeLeft).toFloat()
                    if(timeLeft%60000/1000 < 10){
                        textView.text = (timeLeft/60000).toString() + ":0" + (timeLeft%60000/1000).toString()
                    }else {
                        textView.text = (timeLeft / 60000).toString() + ":" + (timeLeft % 60000 / 1000).toString()
                    }
                }
                override fun onFinish() {
                    running = false
                    count++
                    updateQueue()
                    progressBar.progress = progressBar.progressMax
                    textView.text = "Done!"
                }
            }
        }
        paused = false
        stopped = false
        timer.start()
    }
}