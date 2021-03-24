package com.example.workoutapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.workoutapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    companion object{
        fun saveWorkout(name: String){
            database = Firebase.database.reference
            database.child("Workouts").child("name")
        }
        fun saveExercise(workoutName: String, exercise: Exercise){
            database = Firebase.database.reference
            database.child("Workouts").child(workoutName).setValue(exercise)
        }

        private lateinit var database: DatabaseReference
        var workout = mutableListOf<String>()
        var workouts = mutableMapOf<String, MutableList<Exercise>>()
        var keys = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        database = Firebase.database.reference
        val navHostFrameLayout = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFrameLayout.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    private fun loadData(){

    }
}