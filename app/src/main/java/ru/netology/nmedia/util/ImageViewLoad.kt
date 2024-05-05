package ru.netology.nmedia.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.netology.nmedia.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_downloading_24)
        .error(R.drawable.ic_error_outline_24)
        .timeout(30_000)
        .circleCrop()
        .into(this)
}