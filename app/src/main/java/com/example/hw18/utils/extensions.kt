package com.example.hw18.utils

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun ImageView.setImage(image: String) {
    Glide.with(this.context)
        .load(image)
        .into(this)
}