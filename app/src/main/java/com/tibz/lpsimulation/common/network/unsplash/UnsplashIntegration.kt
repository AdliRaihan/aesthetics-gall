package com.tibz.lpsimulation.common.network.unsplash

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type
import java.util.concurrent.Executors

class UnsplashIntegration: UnsplashIntegrationProtocols.Network {
    companion object {
        const val accessKey = "Client-ID 158e22d2be6cab776308c3250270a395cc2813ca8346c59643b949a4c68ae513"
        const val secretKey = "55c7dc2458c517c02b2797f92f9707f1195e28cf10ab18cb1bf6c238932fc7c3"
    }

    private val getClient: OkHttpClient
        get() {
            return OkHttpClient()
        }

    override fun <T : Type> get(
        expectedData: T,
        specifications: UnsplashBusinessModel.UnsplashSpecifications,
        onReturn: (String?) -> Unit
    ) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(specifications.uri)
            .addHeader("Authorization", accessKey)
            .build()

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        Log.d("UN Hit To", specifications.uri.toString())

        executor.execute {
            try {
                client.newCall(request).execute().apply {
                    Log.d("Unsplash Response Body", this.networkResponse?.body.toString())
                    Log.d("Unsplash Direct Bod", this.peekBody(2048).string())
                    handler.post {
                        onReturn.invoke(this.body?.string())
                    }
                }
            }
            catch (e: Exception) {
                Log.d("Network Fail", e.message ?: "No Error Known")
            }
        }
    }

    override fun <T> someGet(
        expectedData: T,
        specifications: UnsplashBusinessModel.UnsplashSpecifications,
        onReturn: (T) -> Unit
    ) {

    }
}