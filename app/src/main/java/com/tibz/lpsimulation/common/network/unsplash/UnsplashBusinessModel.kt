package com.tibz.lpsimulation.common.network.unsplash

import java.lang.reflect.Type
import java.net.URL
import java.util.*

class UnsplashBusinessModel {
    companion object {
        private const val baseAPI = "https://api.unsplash.com/"
        const val pathPhotos: String = baseAPI + "photos"
        const val pathSearchPhotos: String = baseAPI + "search/photos"
        fun getUser(username: String): String = baseAPI + "users/${username}"
        fun getUserPhoto(username: String): String = baseAPI + "users/${username}/photos"
    }

    data class UnsplashSpecifications(
        var uri: URL,
        var parameter: Map<String, Any>? = null
    )

    data class UnsplashReturnServer<T: Type>(
//        var success: UnsplashMainModel<T>?,
        var failed: Any // TBD
    )
}