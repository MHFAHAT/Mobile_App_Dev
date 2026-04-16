package com.example.universityeventapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.universityeventapp.databinding.ActivitySeatBookingBinding
import kotlin.random.Random

class SeatBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatBookingBinding
    private val seatStates = mutableListOf<Int>() // 0: Available, 1: Booked, 2: Selected
    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event = intent.getParcelableExtra<Event>("EVENT_DATA")
        
        // Initialize seats (6x8 = 48)
        repeat(48) {
            // Randomly mark ~30% as booked
            val state = if (Random.nextFloat() < 0.3f) 1 else 0
            seatStates.add(state)
        }

        val adapter = SeatAdapter(this, seatStates)
        binding.seatGrid.adapter = adapter

        binding.seatGrid.setOnItemClickListener { _, _, position, _ ->
            if (seatStates[position] == 1) {
                Toast.makeText(this, "This seat is already booked", Toast.LENGTH_SHORT).show()
            } else {
                // Toggle between Available (0) and Selected (2)
                seatStates[position] = if (seatStates[position] == 0) 2 else 0
                adapter.notifyDataSetChanged()
                updateSummary()
            }
        }

        binding.btnConfirmBooking.setOnClickListener {
            val selectedCount = seatStates.count { it == 2 }
            if (selectedCount > 0) {
                Toast.makeText(this, "Booking confirmed for $selectedCount seats!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSummary() {
        val selectedCount = seatStates.count { it == 2 }
        val price = event?.price ?: 0.0
        val total = selectedCount * price
        binding.tvSelectionSummary.text = "$selectedCount seats selected"
        binding.tvTotalPrice.text = "Total Price: $${String.format("%.2f", total)}"
    }

    override fun onBackPressed() {
        if (seatStates.any { it == 2 }) {
            AlertDialog.Builder(this)
                .setTitle("Discard Selection?")
                .setMessage("You have selected seats. Are you sure you want to leave?")
                .setPositiveButton("Yes") { _, _ -> super.onBackPressed() }
                .setNegativeButton("No", null)
                .show()
        } else {
            super.onBackPressed()
        }
    }
}
