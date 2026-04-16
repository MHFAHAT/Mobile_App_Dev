package com.example.universityeventapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.databinding.ItemEventBinding

class EventAdapter(
    private var events: List<Event>,
    private val onItemClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.binding.apply {
            eventTitle.text = event.title
            eventDate.text = "${event.date} | ${event.time}"
            eventVenue.text = event.venue
            eventSeats.text = "${event.availableSeats} seats left"
            eventPrice.text = if (event.price == 0.0) "Free" else "$${event.price}"
            eventImage.setImageResource(event.imageRes)
            root.setOnClickListener { onItemClick(event) }
        }
    }

    override fun getItemCount() = events.size

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
