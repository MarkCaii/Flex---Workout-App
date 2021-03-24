package com.example.workoutapp

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.FragmentExercisesListBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WorkoutListFragment : Fragment() {
    private var _binding: FragmentExercisesListBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = WorkoutAdapter(MainActivity.workout)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_add_workout ->{
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.add_workout_dialog, null)
                // AlertDialogBuilder
                val mBuilder = context?.let {
                    AlertDialog.Builder(it)
                        .setView(mDialogView)
                        .setTitle("Add Workout")
                }

                val mAlertDialog = mBuilder?.show()
                val dialogConfirmbtn = mDialogView.findViewById<Button>(R.id.dialog_add_workout)
                val dialogCancelbtn = mDialogView.findViewById<Button>(R.id.dialog_cancel_workout)
                dialogConfirmbtn.setOnClickListener{
                    // dismiss dialog
                    mAlertDialog?.dismiss()
                    val editText = mDialogView.findViewById<EditText>(R.id.dialogNameEt)
                    val name = editText.text.toString()
                    if(name in MainActivity.workouts){
                        Toast.makeText(context, "Duplicate Name", Toast.LENGTH_SHORT).show()
                    }else{
                        MainActivity.workout.add(name)
                        MainActivity.saveWorkout(name)
                        MainActivity.workouts[name] = mutableListOf<Exercise>()
                    }
                }
                dialogCancelbtn.setOnClickListener {
                    mAlertDialog?.dismiss()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}