package com.example.photogalleryapp

data class Photo(
    val Id: Int,
    val ResourceId: Int,
    val Title: String,
    val Category: String,
    var IsSelected: Boolean = false
)