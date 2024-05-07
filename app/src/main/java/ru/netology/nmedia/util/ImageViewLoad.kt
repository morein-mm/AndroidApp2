package ru.netology.nmedia.util

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.netology.nmedia.R


fun ImageView.loadAvatar(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_downloading_24)
        .error(R.drawable.ic_error_outline_24)
        .timeout(30_000)
        .circleCrop()
        .into(this)
}

fun ImageView.loadAttachment(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_downloading_24)
        .error(R.drawable.ic_error_outline_24)
        .override(Resources.getSystem().displayMetrics.widthPixels)
        .timeout(30_000)
        .into(this)
}