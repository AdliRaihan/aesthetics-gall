package com.tibz.lpsimulation.activities.controller.profile

import android.annotation.SuppressLint
import android.app.ActivityManager.MemoryInfo
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tibz.lpsimulation.activities.reusable.views.LPImageDockerView
import com.tibz.lpsimulation.common.extension.animateAlphaTo
import com.tibz.lpsimulation.common.extension.currencyFormat
import com.tibz.lpsimulation.common.extension.divideEqual
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.network.unsplash.UnsplashImageLoader
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.CompImageDockerBinding
import com.tibz.lpsimulation.databinding.VcProfileBinding
import java.lang.ref.WeakReference

/*
    ==========================
    This is just bunch of views logic so that the controller looks more
    readable than it actually was
    ==========================
*/
class ProfileView(
    private val viewBinding: VcProfileBinding,
    private val memInfo: MemoryInfo
    ): LPImageDockerView.LPImageDockerViewInit {

    private var images: MutableList<UnsplashPhotos.Response.Details> = mutableListOf()
    private var userInformation = UnsplashPhotos.Response.User()
    private var adapterDocker = LPImageDockerView(WeakReference(this), images)

    val getBinding: VcProfileBinding
    get() = viewBinding

    fun setupUI(
        username: String
    ) {
        viewBinding.navigationGrabTicker.visibility = View.GONE
        viewBinding.appUsername.text = username
        viewBinding.imageDocker.adapter = adapterDocker
        bindListener()
    }
    fun bindUserInformation(
        userInfo: UnsplashPhotos.Response.User,
    ) {
        viewBinding.scrollView.animateAlphaTo(1f)
        userInformation = userInfo
        userInformation.also {
            viewBinding.imageHolder.loadImage(it.profileImage?.large)
            viewBinding.profileBio.text = it.bio
            viewBinding.profileUsername.text = it.name
            if (it.bio != null)  viewBinding.profileBio.visibility = View.VISIBLE
            else viewBinding.profileBio.visibility = View.GONE
        }
        viewBinding.debugPosition.text = "${memInfo.availMem / 0x100000L}"
    }
    fun bindUserByApi(
        userInfo: UnsplashPhotos.Response.User
    ) {
        userInformation = userInfo
        userInformation.also {
            viewBinding.profileBio.text = it.bio
            viewBinding.profileUsername.text = it.name
            if (it.bio != null)  viewBinding.profileBio.visibility = View.VISIBLE
            else viewBinding.profileBio.visibility = View.GONE
            viewBinding.totalFollowers.text = it.followersCount.toString().currencyFormat()
            viewBinding.totalFollowing.text = it.followingCount.toString().currencyFormat()
            viewBinding.totalLikes.text = it.totalLikes.toString().currencyFormat()
        }
    }
    fun bindPhotosUser(
        images: MutableList<UnsplashPhotos.Response.Details>
    ) {
        this.images.addAll(images)
        adapterDocker = LPImageDockerView(WeakReference(this), images)
        val dockerSizeSquare = viewBinding.imageDocker.width/3
        adapterDocker.notifyDataSetChanged()
        viewBinding.imageDocker.adapter = adapterDocker
        viewBinding.imageDocker.layoutParams.height = dockerSizeSquare * (this.images.size
            .divideEqual()) + dockerSizeSquare/2
    }
    override fun createView(viewGroup: ViewGroup, position: Int): View {
        val posView = CompImageDockerBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        ).also {
            if (this.images.size < position) return it.root
            val dockerSizeSquare = viewBinding.imageDocker.width/3
            val currentItem = this.images[position]
            it.imageDockerView.layoutParams.height = dockerSizeSquare
            UnsplashImageLoader.loadImageTarget(
                WeakReference(it.imageDockerView),
                currentItem.urls?.small,
                90
            )
        }
        return posView.root
    }
    @SuppressLint("SetTextI18n")
    private fun bindListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewBinding.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
                val y = viewBinding.scrollView.scrollY
                val height = viewBinding.containerInfo.height
                val currentAlpha = viewBinding.navigationGrabTicker.alpha
                if (y <= height)
                    viewBinding.navigationGrabTicker.alpha = 0f
                else
                    viewBinding.navigationGrabTicker.alpha = 1f

                if (currentAlpha < 0.05)
                    viewBinding.navigationGrabTicker.visibility = View.GONE
                else
                    viewBinding.navigationGrabTicker.visibility = View.VISIBLE

                viewBinding.debugPosition.text = "${memInfo.availMem / 0x100000L} $y ${viewBinding.containerInfo.height}"
            }
        }
    }
    fun profileDestroyAll(completion: (() -> Unit)?) {
        viewBinding.root.removeAllViews()
        completion?.invoke()
    }
}