package com.tibz.lpsimulation.activities.controller.Home.VP

import com.google.gson.Gson
import com.tibz.lpsimulation.common.network.unsplash.UnsplashBusinessModel
import com.tibz.lpsimulation.common.network.unsplash.UnsplashIntegration
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import java.net.URL

class HomeTabbarViewModel {
    private val uNetwork = UnsplashIntegration()
    var uPage = 1
    var searchPhotosQuery = ""
    val commitOnSearch: Boolean
    get() = searchPhotosQuery != ""

    fun getAllPhotos(
        next: Boolean = false,
        result: (Array<UnsplashPhotos.Response.Details>?) -> Unit
    ) {
        val target = UnsplashBusinessModel.UnsplashSpecifications(
                URL(UnsplashBusinessModel.pathPhotos)
            )

        if (next) uPage += 1

        target.parameter = mapOf(
            "page" to uPage
        )

        uNetwork.get(
            target
        ) {
            // double casting because inconsistent response
            // sometimes array without any key load
            val data = Gson().fromJson(it, Array<UnsplashPhotos.Response.Details>::class.java)
            result(data)
        }
    }

    fun getPhotos(
        next: Boolean = false,
        result: (UnsplashSearchPhotos.Details?) -> Unit
    ) {
        val target = UnsplashBusinessModel.UnsplashSpecifications(
            URL(UnsplashBusinessModel.pathSearchPhotos)
        )
        if (next) uPage += 1
        // assign parameters
        val parameters = mutableMapOf<String, Any>()
        // checking if query
        if (searchPhotosQuery != "")
            parameters["query"] = searchPhotosQuery

        parameters["page"] = uPage
        // make parameters
        target.parameter = parameters
        uNetwork.getV2(target) { code, didsuccess, response ->
            if (code != 200 || !didsuccess) result(null)
            try {
                val data = Gson().fromJson(
                    response,
                    UnsplashSearchPhotos.Details::class.java
                )
                result(data)
            } catch(_: Exception) {
                result(null)
            }
        }
    }

    fun getDarkPhotos(
        next: Boolean = false,
        result: (UnsplashSearchPhotos.Details?) -> Unit
    ) {
        val target = UnsplashBusinessModel.UnsplashSpecifications(
            URL(UnsplashBusinessModel.pathSearchPhotos)
        )

        if (next) uPage += 1

        target.parameter = mapOf(
            "query" to "night street view",
            "color" to "black",
            "orientation" to "landscape"
        )

        uNetwork.getV2(target) { code, didsuccess, response ->
            try {
                val data = Gson().fromJson(
                    response,
                    UnsplashSearchPhotos.Details::class.java
                )
                result(data)
            } catch(_: Exception) {
                result(null)
            }
        }
    }
}