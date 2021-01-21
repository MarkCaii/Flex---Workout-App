package com.example.workoutapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Exercise(
    val exercise: String,
    val sets: Int,
    val reps: Int
){
}