package com.tibz.lpsimulation.activities.controller.profile

import com.google.gson.Gson
import com.tibz.lpsimulation.common.network.Network
import com.tibz.lpsimulation.common.network.unsplash.UnsplashBusinessModel
import com.tibz.lpsimulation.common.network.unsplash.UnsplashIntegration
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import java.net.URL

class ProfileVM {
    private val uNetwork: UnsplashIntegration
    get() = UnsplashIntegration()
    fun getUser(
        result: (UnsplashPhotos.Response.User?) -> Unit
    ) {
        uNetwork.getV2(
            UnsplashBusinessModel.UnsplashSpecifications(
                URL(UnsplashBusinessModel.getUser(ProfileVC.username))
            )
        ) { code, didSuccess, response ->
            try {
                val data = Gson().fromJson(
                    response,
                    UnsplashPhotos.Response.User::class.java
                )
                result(data)
            } catch (_: Exception) { }
        }
    }
    fun getUserPhoto(
        result: (Array<UnsplashPhotos.Response.Details>?) -> Unit
    ) {
        val target = UnsplashBusinessModel.UnsplashSpecifications(
                URL(UnsplashBusinessModel.getUserPhoto(ProfileVC.username))
            )
        target.parameter = mapOf(
            "per_page" to 50
        )
        uNetwork.getV2(target) { code, didSuccess, response ->
            try {
                val data = Gson().fromJson(
                    response,
                    Array<UnsplashPhotos.Response.Details>::class.java
                )
                result(data)
            } catch (_: Exception) { }
        }
    }
}