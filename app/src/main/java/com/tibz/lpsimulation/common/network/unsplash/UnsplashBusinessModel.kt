package com.tibz.lpsimulation.common.network.unsplash

import java.lang.reflect.Type
import java.net.URL
import java.util.*

class UnsplashBusinessModel {
    companion object {
        private const val baseAPI = "https://api.unsplash.com/"
        const val pathPhotos: String = baseAPI + "photos/"
    }

    data class UnsplashSpecifications(
        var uri: URL,
        var parameter: Dictionary<String, Any>? = null
    )

    data class UnsplashReturnServer<T: Type>(
//        var success: UnsplashMainModel<T>?,
        var failed: Any // TBD
    )
}