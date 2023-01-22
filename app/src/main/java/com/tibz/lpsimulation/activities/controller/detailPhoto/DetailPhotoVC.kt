package com.tibz.lpsimulation.activities.controller.detailPhoto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.VcDetailPhotoBinding

class DetailPhotoVC: AppCompatActivity() {
    private lateinit var viewBinding: VcDetailPhotoBinding
    companion object {
        var detailPhoto: UnsplashPhotos.Response.Details? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = VcDetailPhotoBinding.inflate(this.layoutInflater)
        setContentView(viewBinding.root)
        bindPhotoDetails()
    }
    fun bindPhotoDetails() {
        detailPhoto?.also {
            viewBinding.imageView.loadImage(it.urls?.regular)
        }
    }
}