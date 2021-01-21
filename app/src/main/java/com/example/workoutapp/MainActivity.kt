package com.example.workoutapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.workoutapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private lateinit var database: DatabaseReference

    companion object{
        var workout = mutableListOf<String>("Ab Workout","Hypertrophy Chest Workout", "Hypertrophy Arm Workout", "HyperTrophy Leg Workout", "Body Weight Chest Workout", "Body Weight Leg Workout", "Full body workout", "Ab workout: Floor", "Ab workout: Bars")
        var workouts = mutableMapOf<String, MutableList<Exercise>>("Test 2" to mutableListOf<Exercise>(), "Test 3" to mutableListOf<Exercise>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        val exercise = Exercise("Bench", 3, 3)
        database.child("Test").setValue("hi")
        

        val navHostFrameLayout = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFrameLayout.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}