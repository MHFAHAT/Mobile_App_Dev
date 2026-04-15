package com.example.contactbookapp

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.*

class ContactAdapter(context: Context, private var contactList: MutableList<Contact>) :
    ArrayAdapter<Contact>(context, R.layout.item_contact, contactList) {

    private var filteredList: MutableList<Contact> = contactList

    // ViewHolder for performance
    private class ViewHolder {
        lateinit var avatar: TextView
        lateinit var name: TextView
        lateinit var phone: TextView
    }

    override fun getCount(): Int = filteredList.size
    override fun getItem(position: Int): Contact? = filteredList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder()
            holder.avatar = view.findViewById(R.id.AvatarCircle)
            holder.name = view.findViewById(R.id.ContactName)
            holder.phone = view.findViewById(R.id.ContactPhone)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val contact = getItem(position)!!
        holder.name.text = contact.Name
        holder.phone.text = contact.Phone
        holder.avatar.text = contact.Initial

        // Dynamic background color based on first letter
        val colors = arrayOf("#F44336", "#9C27B0", "#3F51B5", "#009688", "#FF9800", "#795548")
        val colorIndex = contact.Initial[0].code % colors.size
        holder.avatar.background.setTint(Color.parseColor(colors[colorIndex]))

        return view
    }

    // Custom filtering logic
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            contactList
        } else {
            contactList.filter { it.Name.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}