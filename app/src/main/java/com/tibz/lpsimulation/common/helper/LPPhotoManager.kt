package com.tibz.lpsimulation.common.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors

class LPPhotoManager {
    companion object {
        fun resizeImage(context: Context?, image: Uri): Bitmap? {
            BitmapFactory
                .decodeStream(
                    context?.contentResolver?.openInputStream(image)
                ).let { data ->
                    var width = data.width / 2
                    var height = data.height / 2
                    var scale = 2
                    BitmapFactory.decodeStream(
                        context?.contentResolver?.openInputStream(image),
                        null,
                        BitmapFactory.Options().apply {
                            this.inSampleSize = scale
                        }
                    )?.let { it1 ->
                        return it1
                    }
                }
            return null
        }

        fun loadImage(uri: Uri?, to: ImageView) {
            Picasso.get().load(uri).into(to)
        }
    }
}