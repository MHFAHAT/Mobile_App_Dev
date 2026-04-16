package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universityeventapp.databinding.ActivityEventsListBinding

class EventsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventsListBinding
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EventAdapter(SampleData.events) { event ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("EVENT_DATA", event)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val category = when (checkedId) {
                R.id.chipTech -> "Tech"
                R.id.chipSports -> "Sports"
                R.id.chipCultural -> "Cultural"
                R.id.chipAcademic -> "Academic"
                R.id.chipSocial -> "Social"
                else -> "All"
            }
            filterEvents(category)
        }
    }

    private fun filterEvents(category: String) {
        val filtered = if (category == "All") {
            SampleData.events
        } else {
            SampleData.events.filter { it.category == category }
        }
        adapter.updateData(filtered)
    }
}
