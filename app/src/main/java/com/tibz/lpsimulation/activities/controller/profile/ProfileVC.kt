package com.tibz.lpsimulation.activities.controller.profile

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.tibz.lpsimulation.activities.reusable.views.LPCalendarView
import com.tibz.lpsimulation.activities.reusable.views.LPImageDockerView
import com.tibz.lpsimulation.activities.view.LoaderView
import com.tibz.lpsimulation.common.extension.currencyFormat
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.CompCalendarNumberBinding
import com.tibz.lpsimulation.databinding.CompImageDockerBinding
import com.tibz.lpsimulation.databinding.VcProfileBinding
import java.lang.ref.WeakReference

class ProfileVC: AppCompatActivity() {
    private var viewModel = ProfileVM()
    private lateinit var currentView: ProfileView
    private lateinit var loaderView: LoaderView

    companion object {
        var username: String = ""
        var userProfileInformation: UnsplashPhotos.Response.User? = UnsplashPhotos.Response.User()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loaderView = LoaderView.createUI(this)

        val memoryInfo = MemoryInfo()
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)

        currentView = ProfileView(
            VcProfileBinding.inflate(this.layoutInflater),
            memoryInfo
        )
        setContentView(currentView.getBinding.root)
        currentView.getBinding.scrollView.alpha = 0f
        currentView.setupUI(
            "@${username}"
        )
        loaderView.display(this, currentView.getBinding.root)
        userProfileInformation?.let { currentView.bindUserInformation(it) }
        loadProfile()
    }
    override fun onDestroy() {
        super.onDestroy()
        currentView.profileDestroyAll {

        }
    }
    private fun loadProfile() {
        viewModel.getUser {
            if (it == null) return@getUser
            currentView.bindUserByApi(it)
            loaderView.hide(currentView.getBinding.root)
        }
        viewModel.getUserPhoto {
            if (it == null) return@getUserPhoto
            currentView.bindPhotosUser(it.toMutableList())
        }
    }
}