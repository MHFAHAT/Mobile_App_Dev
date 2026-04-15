package com.example.photogalleryapp

import android.content.Context
import android.view.*
import android.widget.*

class PhotoAdapter(private val context: Context, private var allPhotos: MutableList<Photo>) : BaseAdapter() {

    private var displayList: MutableList<Photo> = allPhotos
    var isSelectionMode = false

    override fun getCount(): Int = displayList.size
    override fun getItem(position: Int): Photo = displayList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
        val photo = getItem(position)

        val imageView = view.findViewById<ImageView>(R.id.PhotoImage)
        val titleView = view.findViewById<TextView>(R.id.PhotoTitle)
        val checkBox = view.findViewById<CheckBox>(R.id.SelectionCheck)

        imageView.setImageResource(photo.ResourceId)
        titleView.text = photo.Title

        // Ensure checkbox visibility and state are synced
        checkBox.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        checkBox.isChecked = photo.IsSelected

        // Prevent checkbox from stealing click events from the GridView
        checkBox.isClickable = false
        checkBox.isFocusable = false

        return view
    }

    fun filter(category: String) {
        displayList = if (category == "All") {
            allPhotos
        } else {
            allPhotos.filter { it.Category == category }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun removeSelected() {
        allPhotos.removeAll { it.IsSelected }
        filter("All") // Refresh the view
    }
}