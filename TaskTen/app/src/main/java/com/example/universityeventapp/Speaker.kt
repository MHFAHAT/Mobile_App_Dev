package com.example.universityeventapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Speaker(
    val name: String,
    val designation: String,
    val imageRes: Int
) : Parcelable
