package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var emptyState: View
    private var productList = mutableListOf<Product>()
    private var filteredList = mutableListOf<Product>()
    private var cartList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        emptyState = findViewById(R.id.emptyState)
        setupDummyData()
        setupRecyclerView()
        setupTouchHelper()
        setupFilters()
        
        showSkeletonAndLoadData()
    }

    private fun showSkeletonAndLoadData() {
        val skeletonList = List(3) { Product(it, "", 0.0, 0f, "", 0, isLoading = true) }
        adapter.submitList(skeletonList)
        
        recyclerView.postDelayed({
            updateList()
        }, 2000)
    }

    private fun setupDummyData() {
        productList = mutableListOf(
            Product(1, "Laptop", 999.99, 4.5f, "Electronics", R.drawable.laptop),
            Product(2, "T-Shirt", 19.99, 4.0f, "Clothing", R.drawable.t_shirt),
            Product(3, "Java Book", 49.99, 4.8f, "Books", R.drawable.java_book),
            Product(4, "Apple", 0.99, 4.2f, "Food", R.drawable.apple),
            Product(5, "Action Figure", 29.99, 4.7f, "Toys", R.drawable.action_figure),
            Product(6, "Headphones", 199.99, 4.3f, "Electronics", R.drawable.headphones)
        )
        filteredList.addAll(productList)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProductAdapter { product ->
            CartManager.addItem(product)
            product.inCart = true
            invalidateOptionsMenu()
            Snackbar.make(recyclerView, "${product.name} added to cart", Snackbar.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.submitList(filteredList)
    }

    private fun setupFilters() {
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupCategory)
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val category = when (checkedIds.firstOrNull()) {
                R.id.chipElectronics -> "Electronics"
                R.id.chipClothing -> "Clothing"
                R.id.chipBooks -> "Books"
                R.id.chipFood -> "Food"
                R.id.chipToys -> "Toys"
                else -> "All"
            }
            filterByCategory(category)
        }
    }

    private fun filterByCategory(category: String) {
        filteredList = if (category == "All") {
            productList.toMutableList()
        } else {
            productList.filter { it.category == category }.toMutableList()
        }
        updateList()
    }

    private fun updateList() {
        adapter.submitList(filteredList.toList())
        emptyState.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupTouchHelper() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos = vh.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                val list = filteredList.toMutableList()
                val item = list.removeAt(fromPos)
                list.add(toPos, item)
                filteredList = list
                adapter.submitList(filteredList)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val deletedProduct = filteredList[position]
                
                val list = filteredList.toMutableList()
                list.removeAt(position)
                filteredList = list
                adapter.submitList(filteredList)

                Snackbar.make(recyclerView, "${deletedProduct.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        val undoList = filteredList.toMutableList()
                        undoList.add(position, deletedProduct)
                        filteredList = undoList
                        adapter.submitList(filteredList)
                    }.show()
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterBySearch(newText)
                return true
            }
        })

        val cartItem = menu.findItem(R.id.action_cart)
        cartItem.title = "Cart (${CartManager.cartItems.size})"
        
        return true
    }

    private fun filterBySearch(query: String?) {
        val list = if (query.isNullOrBlank()) {
            productList
        } else {
            productList.filter { it.name.contains(query, ignoreCase = true) }
        }
        filteredList = list.toMutableList()
        updateList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_view -> {
                toggleViewMode()
                item.setIcon(if (adapter.isGridView) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_agenda)
                true
            }
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                // In real app, pass cart items
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleViewMode() {
        adapter.isGridView = !adapter.isGridView
        recyclerView.layoutManager = if (adapter.isGridView) GridLayoutManager(this, 2)
        else LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
