package com.example.contactbookapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val contactData = mutableListOf<Contact>()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.ContactListView)
        val searchView = findViewById<SearchView>(R.id.ContactSearch)
        val emptyText = findViewById<TextView>(R.id.EmptyState)
        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.AddContactBtn)

        contactAdapter = ContactAdapter(this, contactData)
        listView.adapter = contactAdapter
        listView.emptyView = emptyText


        listView.setOnItemClickListener { _, _, pos, _ ->
            val c = contactAdapter.getItem(pos)!!


            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.CustomToastContainer))


            layout.findViewById<TextView>(R.id.ToastName).text = "Name: ${c.Name}"
            layout.findViewById<TextView>(R.id.ToastEmail).text = "Email: ${c.Email}"
            layout.findViewById<TextView>(R.id.ToastNumber).text = "Phone: ${c.Phone}"


            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }

        // Long Press: Delete
        listView.setOnItemLongClickListener { _, _, pos, _ ->
            val selected = contactAdapter.getItem(pos)!!
            AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Delete ${selected.Name}?")
                .setPositiveButton("Delete") { _, _ ->
                    contactData.remove(selected)
                    contactAdapter.filter(searchView.query.toString())
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }

        // Real-time Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                contactAdapter.filter(newText ?: "")
                return true
            }
        })

        // Add Contact FAB
        fab.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
            AlertDialog.Builder(this)
                .setTitle("New Contact")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val name = dialogView.findViewById<EditText>(R.id.InputName).text.toString()
                    val phone = dialogView.findViewById<EditText>(R.id.InputPhone).text.toString()
                    val email = dialogView.findViewById<EditText>(R.id.InputEmail).text.toString()

                    if (name.isNotBlank()) {
                        contactData.add(Contact(name, phone, email))
                        contactAdapter.filter(searchView.query.toString())
                    }
                }
                .setNegativeButton("Cancel", null).show()
        }
    }
}