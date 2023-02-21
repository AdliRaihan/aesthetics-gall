package com.tibz.lpsimulation.common.network.unsplash

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.tibz.lpsimulation.common.extension.randomizerByRange
import java.io.*
import java.lang.Exception

class UnsplashDownloader(
    private val from: AppCompatActivity
): com.squareup.picasso.Target {
    var url: Uri? = null
    fun downloadImageFor() {
        if (url == null) return
        Log.d("Is Permission", "Download image")
        Picasso.get().load(url.toString()).into(this)
    }
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        if (bitmap == null) return
        saveImageFrom(bitmap = bitmap)
    }
    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        Log.d("Is Permission", "Download image failed")
    }
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        Log.d("Is Permission", "Download image On load")
    }
    private fun saveImageFrom(bitmap: Bitmap) {
        Log.d("Is Permission", "Trying to save image")
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
            }
        }
        val resolver = from.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            resolver.openOutputStream(uri).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            }
        }
    }
}