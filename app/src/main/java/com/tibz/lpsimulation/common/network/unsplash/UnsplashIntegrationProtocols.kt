package com.tibz.lpsimulation.common.network.unsplash

import java.lang.reflect.Type

interface UnsplashIntegrationProtocols {

    /**
     * Unsplash having inconsistent data for returning response
     * this makes us (developer) hard to make generic data model
     * FUCK YOU!
     */
    interface Network {
        fun get(
            specifications: UnsplashBusinessModel.UnsplashSpecifications,
            onReturn: (String?) -> Unit
        )
        fun getV2(
            specifications: UnsplashBusinessModel.UnsplashSpecifications,
            onReturn: (code: Int, didSuccess: Boolean, Response: String?) -> Unit
        )
    }
}