package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.FragmentExercisesListBinding

class  ExercisesListFragment : Fragment() {

    companion object{
        val NAME = "workout_name"
    }
    private var _binding: FragmentExercisesListBinding? = null

    private val binding get() = _binding!!

    private lateinit var workoutName: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let{
            workoutName = it.getString(NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ExerciseAdapter(MainActivity.workouts[workoutName]!!, requireContext())
        if(MainActivity.workouts[workoutName]!!.size > 0){
            binding.addExerciseHint.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_exercise_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_add_exercise->{
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.add_exercise_dialog, null)
                // AlertDialogBuilder
                val mBuilder = context?.let {
                    AlertDialog.Builder(it)
                        .setView(mDialogView)
                        .setTitle("Add Exercise")
                }

                val mAlertDialog = mBuilder?.show()
                val dialogLoginbtn = mDialogView.findViewById<Button>(R.id.dialog_add_exercise)
                val dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialog_cancel_exercise)
                dialogLoginbtn.setOnClickListener{
                    // dismiss dialog
                    mAlertDialog?.dismiss()

                    val editText = mDialogView.findViewById<EditText>(R.id.dialog_exercise)
                    val editTextSets = mDialogView.findViewById<EditText>(R.id.dialog_sets)
                    val editTextReps = mDialogView.findViewById<EditText>(R.id.dialog_reps)

                    val sets = editTextSets.text.toString().toInt()
                    val reps = editTextReps.text.toString().toInt()
                    val exerciseName = editText.text.toString()
                    val exercise = Exercise(exerciseName, sets, reps)
                    MainActivity.workouts.getValue(workoutName).add(exercise)
                }
                dialogCancelBtn.setOnClickListener {
                    mAlertDialog?.dismiss()
                }
                return true
            }
            R.id.action_start_workout->{
                val timerDialogView = LayoutInflater.from(context).inflate(R.layout.timer_dialog, null)

                // AlertDialogBuilder
                val numberPickerMinutes = timerDialogView.findViewById<NumberPicker>(R.id.dialog_minutes)
                val numberPickerSeconds = timerDialogView.findViewById<NumberPicker>(R.id.dialog_seconds)
                numberPickerMinutes.maxValue = 59
                numberPickerSeconds.maxValue = 59
                if(MainActivity.workouts[workoutName]?.size  == 0){
                    Toast.makeText(context, "Can't start an empty workout", Toast.LENGTH_SHORT).show()
                }else{
                    val mBuilder = context?.let {
                        AlertDialog.Builder(it)
                            .setView(timerDialogView)
                            .setTitle("Set Timer")
                    }
                    val setTimerDialog = mBuilder?.show()
                    val dialogConfirmBtn = timerDialogView.findViewById<Button>(R.id.dialog_confirm)
                    val dialogCancelBtn = timerDialogView.findViewById<Button>(R.id.dialog_cancel)
                    dialogConfirmBtn.setOnClickListener{
                        // dismiss dialog
                        setTimerDialog?.dismiss()

                        if(numberPickerMinutes.value.toString() == "" || numberPickerSeconds.value.toString() == ""){
                            Toast.makeText(context, "Left a field blank", Toast.LENGTH_SHORT).show()
                        }else{
                            val seconds = numberPickerSeconds.value.toString()
                            val minutes = numberPickerMinutes.value.toString()
                            Log.d("MainActivity", seconds)
                            Log.d("MainActivity", minutes)
                            val intent = Intent(context, RestTimerActivity::class.java)

                            intent.putExtra("SECONDS", seconds)
                            intent.putExtra("MINUTES", minutes)
                            intent.putExtra("WORKOUT_NAME", workoutName)
                            startActivity(intent)
                        }
                    }
                    dialogCancelBtn.setOnClickListener{
                        setTimerDialog?.dismiss()
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}