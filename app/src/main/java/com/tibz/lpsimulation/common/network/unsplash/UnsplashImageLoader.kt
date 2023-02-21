package com.tibz.lpsimulation.common.network.unsplash

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.ref.WeakReference
import java.net.URL

class UnsplashImageLoader: ViewModel() {
    companion object {
        fun loadImageTarget(
            into: WeakReference<ImageView>?,
            url: String?,
            quality: Int
        ) {
            val unsplashImageLoader = UnsplashImageLoader()
            unsplashImageLoader.url = url
            unsplashImageLoader.imageRef = into
            unsplashImageLoader.requestCreator = Picasso.get().load(url.toString())
            unsplashImageLoader.startLoad()
            unsplashImageLoader.quality = quality
        }
    }

    var url: String? = null
    var imageRef: WeakReference<ImageView>? = null
    var requestCreator: RequestCreator? = null
    var quality = 0
    fun startLoad() {
        viewModelScope.launch(Dispatchers.IO) {
            val urlRequest = URL(url)
            val asBitmap = BitmapFactory.decodeStream(urlRequest.openConnection().getInputStream())
            if (asBitmap != null)
                compressAndShip(asBitmap)
        }
    }
    private fun compressAndShip(originalBitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val bitMap = BitmapFactory.decodeStream(ByteArrayInputStream(outputStream.toByteArray()))
        viewModelScope.launch(Dispatchers.Main) {
            imageRef?.get()?.setImageBitmap(bitMap)
            imageRef?.clear()
        }
    }
}