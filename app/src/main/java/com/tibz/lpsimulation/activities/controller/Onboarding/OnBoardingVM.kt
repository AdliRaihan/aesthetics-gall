package com.tibz.lpsimulation.activities.controller.Onboarding

import com.google.gson.Gson
import com.tibz.lpsimulation.common.network.unsplash.UnsplashBusinessModel
import com.tibz.lpsimulation.common.network.unsplash.UnsplashIntegration
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import java.net.URL

class OnBoardingVM {
    private val uNetwork = UnsplashIntegration()
    fun getDarkPhotos(
        result: (UnsplashSearchPhotos.Details?) -> Unit
    ) {
        val target = UnsplashBusinessModel.UnsplashSpecifications(
            URL(UnsplashBusinessModel.pathSearchPhotos)
        )
        target.parameter = mapOf(
            "query" to "black human",
            "color" to "black",
            "orientation" to "portrait"
        )
        uNetwork.getV2(target) { code, isSuccess, response ->
            try {
                if (code != 200) return@getV2
                val data = Gson().fromJson(
                    response,
                    UnsplashSearchPhotos.Details::class.java
                )
                result(data)
            } catch(_: Exception) { }
        }
    }
}