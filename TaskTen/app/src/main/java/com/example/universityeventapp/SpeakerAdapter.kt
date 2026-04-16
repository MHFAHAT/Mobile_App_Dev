package com.example.universityeventapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.databinding.ItemSpeakerBinding

class SpeakerAdapter(private val speakers: List<Speaker>) :
    RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder>() {

    inner class SpeakerViewHolder(val binding: ItemSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val binding = ItemSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpeakerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.binding.apply {
            speakerName.text = speaker.name
            speakerDesignation.text = speaker.designation
            speakerImage.setImageResource(speaker.imageRes)
        }
    }

    override fun getItemCount() = speakers.size
}
