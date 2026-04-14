package com.example.taskone

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        val etID = findViewById<EditText>(R.id.etID)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etAge = findViewById<EditText>(R.id.etAge)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val cbFootball = findViewById<CheckBox>(R.id.cbFootball)
        val cbCricket = findViewById<CheckBox>(R.id.cbCricket)
        val cbBasketball = findViewById<CheckBox>(R.id.cbBasketball)
        val cbBadminton = findViewById<CheckBox>(R.id.cbBadminton)
        val spinnerCountry = findViewById<Spinner>(R.id.spinnerCountry)
        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // Setup Spinner
        val countries = arrayOf("Select Country", "Bangladesh", "India", "USA", "UK", "Canada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spinnerCountry.adapter = adapter

        // Date Picker Dialog
        btnDatePicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth/${monthOfYear + 1}/$yearSelected"
                tvSelectedDate.text = "Selected Date: $selectedDate"
            }, year, month, day)

            dpd.show()
        }

        // Submit Button Logic
        btnSubmit.setOnClickListener {
            val id = etID.text.toString()
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val ageStr = etAge.text.toString()
            val country = spinnerCountry.selectedItem.toString()

            // Get Gender
            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else ""

            // Get Sports
            val sports = mutableListOf<String>()
            if (cbFootball.isChecked) sports.add("Football")
            if (cbCricket.isChecked) sports.add("Cricket")
            if (cbBasketball.isChecked) sports.add("Basketball")
            if (cbBadminton.isChecked) sports.add("Badminton")

            // Validation
            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() ||
                ageStr.isEmpty() || gender.isEmpty() || selectedDate.isEmpty() || spinnerCountry.selectedItemPosition == 0) {
                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_SHORT).show()
            } else if (!email.contains("@")) {
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show()
            } else if (ageStr.toIntOrNull() ?: 0 <= 0) {
                Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_SHORT).show()
            } else {
                // Success - Show Toast
                val result = """
                    ID: $id
                    Name: $name
                    Gender: $gender
                    Sports: ${sports.joinToString(", ")}
                    Country: $country
                    DOB: $selectedDate
                """.trimIndent()

                AlertDialog.Builder(this)
                    .setTitle("Student Info")
                    .setMessage(result)
                    .setPositiveButton("OK", null)
                    .show()

            }
        }

        // Reset Button Logic
        btnReset.setOnClickListener {
            etID.text.clear()
            etName.text.clear()
            etEmail.text.clear()
            etPassword.text.clear()
            etAge.text.clear()
            rgGender.clearCheck()
            cbFootball.isChecked = false
            cbCricket.isChecked = false
            cbBasketball.isChecked = false
            cbBadminton.isChecked = false
            spinnerCountry.setSelection(0)
            selectedDate = ""
            tvSelectedDate.text = "No date selected"
            Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show()
        }
    }
}