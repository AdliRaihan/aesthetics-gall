package com.tibz.lpsimulation.activities.tabbar

import com.google.gson.Gson
import com.tibz.lpsimulation.common.network.unsplash.UnsplashBusinessModel
import com.tibz.lpsimulation.common.network.unsplash.UnsplashIntegration
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import java.net.URL

class HomeTabbarViewModel {
    private val uNetwork = UnsplashIntegration()
    fun getAllPhotos(result: (Array<UnsplashPhotos.Response.Details>?) -> Unit) {
        uNetwork.get(
            Array<UnsplashPhotos.Response.Details>::class.java,
            UnsplashBusinessModel.UnsplashSpecifications(
                URL(UnsplashBusinessModel.pathPhotos)
            )
        ) {
            // double casting because inconsistent response
            // sometimes array without any key load
            val data = Gson().fromJson(it, Array<UnsplashPhotos.Response.Details>::class.java)
            result(data)
        }
    }
}