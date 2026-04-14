package com.example.taskfour

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var totalPoints = 0.0
    private var subjectCount = 0
    private var passedCount = 0
    private var failedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableBody = findViewById<TableLayout>(R.id.TableBody)
        val inputSubject = findViewById<EditText>(R.id.InputSubject)
        val inputObtained = findViewById<EditText>(R.id.InputObtained)
        val inputTotal = findViewById<EditText>(R.id.InputTotal)
        val addBtn = findViewById<Button>(R.id.AddBtn)
        val gpaDisplay = findViewById<TextView>(R.id.GpaDisplay)
        val summaryDisplay = findViewById<TextView>(R.id.SummaryDisplay)

        addBtn.setOnClickListener {
            val subName = inputSubject.text.toString()
            val obtained = inputObtained.text.toString().toDoubleOrNull() ?: -1.0
            val total = inputTotal.text.toString().toDoubleOrNull() ?: 100.0

            if (subName.isNotEmpty() && obtained in 0.0..total) {
                val percentage = (obtained / total) * 100
                val (grade, gpaPoint) = calculateGrade(percentage)

                val row = TableRow(this)
                row.setPadding(8, 8, 8, 8)

                if (grade == "F") {
                    row.setBackgroundColor(Color.parseColor("#00FF00")) // Light Red
                    failedCount++
                } else {
                    row.setBackgroundColor(Color.parseColor("#FF0000")) // Light Green
                    passedCount++
                }

                // Add data to row
                row.addView(createTextView(subName))
                row.addView(createTextView(obtained.toInt().toString()))
                row.addView(createTextView(total.toInt().toString()))
                row.addView(createTextView(grade, isBold = true))

                tableBody.addView(row)

                subjectCount++
                totalPoints += gpaPoint

                val finalGpa = if (subjectCount > 0) totalPoints / subjectCount else 0.0
                gpaDisplay.text = "GPA: ${String.format("%.2f", finalGpa)}"
                summaryDisplay.text = "Total: $subjectCount | Passed: $passedCount | Failed: $failedCount"

                inputSubject.text.clear()
                inputObtained.text.clear()
            } else {
                Toast.makeText(this, "Please enter valid marks", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateGrade(percent: Double): Pair<String, Double> {
        return when {
            percent >= 90 -> "A+" to 4.0
            percent >= 80 -> "A" to 3.7
            percent >= 70 -> "B+" to 3.3
            percent >= 60 -> "B" to 3.0
            percent >= 50 -> "C" to 2.0
            percent >= 40 -> "D" to 1.0
            else -> "F" to 0.0
        }
    }

    private fun createTextView(text: String, isBold: Boolean = false): TextView {
        val tv = TextView(this)
        tv.text = text
        tv.gravity = Gravity.CENTER
        if (isBold) tv.setTypeface(null, Typeface.BOLD)
        return tv
    }
}