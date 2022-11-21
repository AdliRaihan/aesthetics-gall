package com.tibz.lpsimulation.common.helper

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64.decode
import android.util.Base64.encodeToString
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.Base64

class LPStorageManager(private val activity: AppCompatActivity) {
    var userPhoto: Bitmap? = null

    fun savePhoto(photoUri: Uri) {
        Toast.makeText(activity, photoUri.toString(), Toast.LENGTH_LONG).show()
        LPPhotoManager.resizeImage(activity, photoUri)?.let { img ->
            activity.getSharedPreferences("UserPhotos", Context.MODE_PRIVATE).edit()
                .also {
                    it.putString("profilePicture", encodeImage(img))
                }.apply()
        }
    }

    fun loadPhoto() {
        activity.getSharedPreferences("UserPhotos", Context.MODE_PRIVATE)
            .getString("profilePicture", null)
            ?.let {
                Toast.makeText(activity, "Photo Loaded!", Toast.LENGTH_SHORT).show()
                userPhoto = decodeImage(it)
            }
    }

    fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return encodeToString(b, android.util.Base64.NO_WRAP)
    }

    fun decodeImage(string: String): Bitmap {
        decode(string, android.util.Base64.DEFAULT).also {
            return BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }
}