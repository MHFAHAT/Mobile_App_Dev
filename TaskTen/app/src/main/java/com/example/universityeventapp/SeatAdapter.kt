package com.example.universityeventapp

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SeatAdapter(
    private val context: Context,
    private val seatStates: MutableList<Int> // 0: Available, 1: Booked, 2: Selected
) : BaseAdapter() {

    override fun getCount(): Int = seatStates.size
    override fun getItem(position: Int): Any = seatStates[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = (convertView as? TextView) ?: TextView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                100
            )
            gravity = android.view.Gravity.CENTER
            setTextColor(Color.WHITE)
            textSize = 12f
            setBackgroundResource(android.R.drawable.btn_default)
        }

        textView.text = "${position + 1}"
        
        when (seatStates[position]) {
            0 -> textView.setBackgroundColor(Color.parseColor("#4CAF50")) // Green
            1 -> textView.setBackgroundColor(Color.parseColor("#F44336")) // Red
            2 -> textView.setBackgroundColor(Color.parseColor("#2196F3")) // Blue
        }

        return textView
    }
}
