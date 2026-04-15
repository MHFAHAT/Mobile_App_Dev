package com.example.ecommerceapp

object CartManager {
    val cartItems = mutableListOf<Product>()
    
    fun addItem(product: Product) {
        if (!cartItems.any { it.id == product.id }) {
            cartItems.add(product)
        }
    }
    
    fun removeItem(product: Product) {
        cartItems.removeAll { it.id == product.id }
    }
    
    fun clear() {
        cartItems.clear()
    }
    
    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }
}
