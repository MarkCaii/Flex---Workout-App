package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(private val dataset: MutableList<String>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        val button = view.findViewById<Button>(R.id.button_item)
        val button2 = view.findViewById<ImageButton>(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return WorkoutViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        var item = dataset.get(position)
        holder.button.text = item.toString()

        holder.button.setOnClickListener{
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToExercisesListFragment(workoutName = holder.button.text.toString())
            holder.view.findNavController().navigate(action)
        }
        holder.button2.setOnClickListener{
            MainActivity.workouts.remove(item)
            dataset.remove(item)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}