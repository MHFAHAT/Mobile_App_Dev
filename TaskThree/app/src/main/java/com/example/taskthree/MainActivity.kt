package com.example.taskthree

import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentSteps = 0
    private val goalSteps = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stepsValue = findViewById<TextView>(R.id.StepsValue)
        val progressBar = findViewById<ProgressBar>(R.id.GoalProgressBar)
        val progressPercent = findViewById<TextView>(R.id.ProgressPercent)
        val updateBtn = findViewById<Button>(R.id.UpdateBtn)

        updateBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Update Steps")

            // Create input field for dialog
            val input = EditText(this)
            input.hint = "Enter steps taken"
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setView(input)

            builder.setPositiveButton("Update") { _, _ ->
                val newStepsStr = input.text.toString()
                if (newStepsStr.isNotEmpty()) {
                    currentSteps = newStepsStr.toInt()
                    stepsValue.text = currentSteps.toString()

                    updateProgress(progressBar, progressPercent)
                }
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }

    private fun updateProgress(progressBar: ProgressBar, percentText: TextView) {
        // Calculate percentage
        val progress = ((currentSteps.toFloat() / goalSteps) * 100).toInt()

        // Update UI (cap it at 100)
        val displayProgress = if (progress > 100) 100 else progress
        progressBar.progress = displayProgress
        percentText.text = "$displayProgress%"

        if (progress >= 100) {
            Toast.makeText(this, "Goal Reached! You are a champion! 🏆", Toast.LENGTH_LONG).show()
        }
    }
}