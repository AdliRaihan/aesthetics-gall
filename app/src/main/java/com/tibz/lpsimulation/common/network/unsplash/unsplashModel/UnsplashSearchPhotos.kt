package com.tibz.lpsimulation.common.network.unsplash.unsplashModel

import com.google.gson.annotations.SerializedName

class UnsplashSearchPhotos {
    data class Details (
        @SerializedName("results") var result: Array<UnsplashPhotos.Response.Details>? = null
    )
}