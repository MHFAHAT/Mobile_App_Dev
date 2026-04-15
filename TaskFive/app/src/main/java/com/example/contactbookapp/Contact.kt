package com.example.contactbookapp

data class Contact(
    val Name: String,
    val Phone: String,
    val Email: String,
    val Initial: String = Name.take(1).uppercase()
)
