package com.tibz.lpsimulation.common.network.unsplash

import java.lang.reflect.Type

interface UnsplashIntegrationProtocols {

    /**
     * Unsplash having inconsistent data for returning response
     * this makes us (developer) hard to make generic data model
     * FUCK YOU!
     */
    interface Network {
        fun <T: Type> get(
            expectedData: T,
            specifications: UnsplashBusinessModel.UnsplashSpecifications,
            onReturn: (String?) -> Unit
        )

        fun <T> someGet(
            expectedData: T,
            specifications: UnsplashBusinessModel.UnsplashSpecifications,
            onReturn: (T) -> Unit
        )
    }
}