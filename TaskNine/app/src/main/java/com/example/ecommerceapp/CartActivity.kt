package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var tvTotalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarCart)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerViewCart)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        adapter = ProductAdapter { product ->
            CartManager.removeItem(product)
            updateCartUI()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        
        updateCartUI()

        btnCheckout.setOnClickListener {
            CartManager.clear()
            updateCartUI()
        }
    }

    private fun updateCartUI() {
        adapter.submitList(CartManager.cartItems.toList())
        val total = CartManager.getTotalPrice()
        tvTotalPrice.text = getString(R.string.total_price, total)
    }
}
