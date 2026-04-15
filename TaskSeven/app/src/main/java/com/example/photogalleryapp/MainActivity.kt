package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Photo>()
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImages()

        val grid = findViewById<GridView>(R.id.PhotoGrid)
        val toolbar = findViewById<RelativeLayout>(R.id.SelectionToolbar)
        val countText = findViewById<TextView>(R.id.SelectedCount)
        val fab = findViewById<FloatingActionButton>(R.id.AddPhotoBtn)

        adapter = PhotoAdapter(this, photoList)
        grid.adapter = adapter

        // LONG PRESS: Start Selection Mode
        grid.setOnItemLongClickListener { _, _, pos, _ ->
            if (!adapter.isSelectionMode) {
                adapter.isSelectionMode = true
                adapter.getItem(pos).IsSelected = true
                toolbar.visibility = View.VISIBLE
                updateSelectionCount(countText)
                adapter.notifyDataSetChanged()
            }
            true
        }

        // CLICK: Toggle Selection OR Open Fullscreen
        grid.setOnItemClickListener { _, _, pos, _ ->
            val clickedPhoto = adapter.getItem(pos)

            if (adapter.isSelectionMode) {
                clickedPhoto.IsSelected = !clickedPhoto.IsSelected

                val selectedCount = photoList.count { it.IsSelected }
                if (selectedCount == 0) {
                    adapter.isSelectionMode = false
                    toolbar.visibility = View.GONE
                }
                updateSelectionCount(countText)
                adapter.notifyDataSetChanged()
            } else {
                // IMPORTANT: Use the ResourceId from the data object
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("IMG_RES", clickedPhoto.ResourceId)
                startActivity(intent)
            }
        }

        // DELETE BUTTON
        findViewById<ImageButton>(R.id.DeleteBtn).setOnClickListener {
            val count = photoList.count { it.IsSelected }
            adapter.removeSelected()
            adapter.isSelectionMode = false
            toolbar.visibility = View.GONE
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
        }

        // TAB FILTERING (Simplified)
        val tabs = mapOf(
            R.id.TabAll to "All",
            R.id.TabNature to "Nature",
            R.id.TabCity to "City",
            R.id.TabFood to "Food"
        )

        tabs.forEach { (id, category) ->
            findViewById<Button>(id).setOnClickListener { adapter.filter(category) }
        }

        fab.setOnClickListener {
            val newPhoto = Photo(photoList.size + 1, R.drawable.misc_abstract, "Added Photo", "All")
            photoList.add(newPhoto)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "New photo added!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImages() {
        photoList.add(Photo(1, R.drawable.animal_cat, "Whiskers", "Animals"))
        photoList.add(Photo(2, R.drawable.animal_dog, "Buddy", "Animals"))
        photoList.add(Photo(3, R.drawable.city_skyline, "Skyline", "City"))
        photoList.add(Photo(4, R.drawable.city_street, "Street", "City"))
        photoList.add(Photo(5, R.drawable.food_burger, "Burger", "Food"))
        photoList.add(Photo(6, R.drawable.food_pizza, "Pizza", "Food"))
        photoList.add(Photo(7, R.drawable.nature_forest, "Forest", "Nature"))
        photoList.add(Photo(8, R.drawable.nature_mountain, "Mountain", "Nature"))
        photoList.add(Photo(9, R.drawable.travel_airplane, "Airplane", "Travel"))
        photoList.add(Photo(10, R.drawable.travel_beach, "Beach", "Travel"))
        photoList.add(Photo(11, R.drawable.misc_abstract, "Abstract", "All"))
        photoList.add(Photo(12, R.drawable.misc_portrait, "Portrait", "All"))
    }

    private fun updateSelectionCount(tv: TextView) {
        val currentSelected = photoList.count { it.IsSelected }
        tv.text = "$currentSelected selected"
    }
}