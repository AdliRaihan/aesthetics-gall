package com.tibz.lpsimulation.common.network.unsplash

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.concurrent.Executors
import kotlin.math.exp

class UnsplashIntegration: UnsplashIntegrationProtocols.Network {
    companion object {
        const val accessKey = "Client-ID 158e22d2be6cab776308c3250270a395cc2813ca8346c59643b949a4c68ae513"
        const val secretKey = "55c7dc2458c517c02b2797f92f9707f1195e28cf10ab18cb1bf6c238932fc7c3"
    }

    private val getClient: OkHttpClient
        get() {
            return OkHttpClient()
        }

    override fun get(
        specifications: UnsplashBusinessModel.UnsplashSpecifications,
        onReturn: (String?) -> Unit
    ) {
        val client = getClient
        var request = Request.Builder()
            .url(specifications.uri)
            .addHeader("Authorization", accessKey)
            .build()
        val requestParameter = request.url.newBuilder()
        specifications.parameter?.let { it ->
            it.asIterable().forEach { itl ->
                requestParameter.addQueryParameter(itl.key, itl.value.toString())
            }
        }
        request = Request.Builder()
            .url(requestParameter.build())
            .addHeader("Authorization", accessKey)
            .build()
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                client.newCall(request).execute().apply {
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

    override fun getV2(
        specifications: UnsplashBusinessModel.UnsplashSpecifications,
        onReturn: (code: Int, didSuccess: Boolean, Response: String?) -> Unit
    ) {
        val client = getClient
        var request = Request.Builder()
            .url(specifications.uri)
            .addHeader("Authorization", accessKey)
            .build()
        val requestParameter = request.url.newBuilder()
        specifications.parameter?.let { it ->
            it.asIterable().forEach { itl ->
                requestParameter.addQueryParameter(itl.key, itl.value.toString())
            }
        }
        request = Request.Builder()
            .url(requestParameter.build())
            .addHeader("Authorization", accessKey)
            .build()
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        Log.d("UN Hit To", request.url.toString())
        Log.d("Query", specifications.parameter.toString())
        executor.execute {
            try {
                client.newCall(request).execute().apply {
                    Log.d("Unsplash Response Body", this.code.toString())
                    Log.d("Unsplash Direct Bod", this.peekBody(2048).string())
                    handler.post {
                        onReturn.invoke(this.code, true, this.body?.string())
                    }
                }
            }
            catch (e: Exception) {
                Log.d("Network Fail", e.message ?: "No Error Known")
            }
        }
    }
}