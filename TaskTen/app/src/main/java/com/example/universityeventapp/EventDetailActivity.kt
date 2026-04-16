package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universityeventapp.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<Event>("EVENT_DATA") ?: return

        binding.detailTitle.text = event.title
        binding.detailDateTime.text = "${event.date} | ${event.time}"
        binding.detailVenue.text = event.venue
        binding.detailDescription.text = event.description
        binding.detailImage.setImageResource(event.imageRes)

        // Setup Speakers (Sample data for speakers)
        val speakers = listOf(
            Speaker("Dr. Jane Smith", "Expert in AI", R.drawable.ic_launcher_background),
            Speaker("John Doe", "Senior Developer", R.drawable.ic_launcher_background)
        )
        binding.rvSpeakers.layoutManager = LinearLayoutManager(this)
        binding.rvSpeakers.adapter = SpeakerAdapter(speakers)

        // Gallery - using a simple horizontal RecyclerView for now
        // For simplicity, reusing EventAdapter or creating a simple image adapter
        // Requirement: Photo gallery row - horizontal RecyclerView with event photos
        // Let's just use a placeholder for now

        binding.btnBookSeats.setOnClickListener {
            val intent = Intent(this, SeatBookingActivity::class.java)
            intent.putExtra("EVENT_DATA", event)
            startActivity(intent)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
