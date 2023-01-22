package com.tibz.lpsimulation.common.extension

import android.app.ActionBar.LayoutParams
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import java.lang.Exception

fun loadAsBitmap(url: String?, onComplete: ((Bitmap) -> Unit)?) {
}
fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url?.toUri()).into(this)
}
fun ImageView.loadImage(url: Uri?, resize: Boolean = false) {
    Picasso.get().load(url).into(this)
}
fun ImageView.loadImageWithPreLoad(url: Uri?, preLoadHeight: Int) {
    this.layoutParams.height = preLoadHeight
    Picasso.get().load(url).into(this).also {
        this.layoutParams.height = LayoutParams.WRAP_CONTENT
    }
}

class LPPicassoHandler: com.squareup.picasso.Target {
    private var onComplete: ((Bitmap?, Boolean) -> Unit)? = null
    private var loadedImages: MutableList<Bitmap> = mutableListOf()
    private var imageViews: MutableList<ImageView> = mutableListOf()
    fun loadImage(
        url: String?,
        position: Int,
        into: ImageView,
        onComplete: ((Bitmap?, Boolean) -> Unit)?) {

        if (position < loadedImages.size) {
            loadExistingImage(position)
            return
        }

        this.onComplete = onComplete
        imageViews.add(into)
        Picasso.get().load(url.toString()).into(this)
    }
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        if (bitmap == null) {
            return
        }
        loadedImages.add(bitmap)
        imageViews.last().setImageBitmap(loadedImages.last())
    }
    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        onComplete?.invoke(null, false)
    }
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }
    private fun loadExistingImage(position: Int) {
        imageViews[position].setImageBitmap(loadedImages[position])
    }
}