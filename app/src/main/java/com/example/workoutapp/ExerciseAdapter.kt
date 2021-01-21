package com.example.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val list: MutableList<Exercise>, context: Context) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>(){

    class ExerciseViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById<TextView>(R.id.item_title)
        val textViewSets: TextView = view.findViewById<TextView>(R.id.item_sets)
        val textViewReps: TextView = view.findViewById<TextView>(R.id.item_reps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_view, parent, false)

        return ExerciseViewHolder(layout)

    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        var item = list.get(position)
        holder.textView.text = item.exercise.toString()
        holder.textViewSets.text = "Sets: " + item.sets.toString()
        holder.textViewReps.text = "Reps: " + item.reps.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}