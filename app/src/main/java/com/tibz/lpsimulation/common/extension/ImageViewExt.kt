package com.tibz.lpsimulation.common.extension

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url?.toUri()).into(this)
}

fun ImageView.loadImage(url: Uri?, resize: Boolean = false) {
    Picasso.get().load(url).into(this)
}