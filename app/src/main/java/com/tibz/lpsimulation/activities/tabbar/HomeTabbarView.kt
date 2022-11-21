package com.tibz.lpsimulation.activities.tabbar

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BasePermissionManager
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.common.extension.linearConstruct
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.helper.LPPhotoManager
import com.tibz.lpsimulation.common.helper.LPStorageManager
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.CellPhotosUserHomeBinding
import com.tibz.lpsimulation.databinding.TabbarHomeViewBinding
import java.net.URI

class HomeTabbarView(private var parentHolder: AppCompatActivity): Fragment(),
    SwiftCollectionViewProtocol,
    BottomSheetProtocol {

    private lateinit var viewBinding: TabbarHomeViewBinding
    private lateinit var viewModel: HomeTabbarViewModel
    private val imagePicker: BasePermissionManager = BasePermissionManager(null, this)
    private var userPhoto = LPStorageManager(parentHolder)
    private var listPhotosData: Array<UnsplashPhotos.Response.Details>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = TabbarHomeViewBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerPermission()
        super.onViewCreated(view, savedInstanceState)
        viewModel = HomeTabbarViewModel()
        userPhoto.loadPhoto()
        setupUI()
    }

    private fun setupUI() {
        viewBinding.setProfilePicture.text = "Reload Sections"

        viewBinding.takePhotoDebug.setOnClickListener {
            imagePicker.launchGallery()
        }

        viewBinding.setProfilePicture.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getAllPhotos {
            this.listPhotosData = it
            this.bindData()
        }
    }

    private fun bindData() {
        this.context?.let {
            viewBinding.usersPhotoLists.linearConstruct(it, this, listPhotosData?.size ?: 0)
        }
    }

    private fun registerPermission() {
        imagePicker.registerResultGallery {
            it.data?.data?.apply {
                LPPhotoManager.resizeImage(this@HomeTabbarView.context, this)?.let { img ->
//                    photos.add(img)
                }
                this@HomeTabbarView.bindData()
            }
        }
    }


    // Cell Transaction goes here!
    override fun onItemShowing(row: Int, item: ViewBinding) {
        (item as? CellPhotosUserHomeBinding)?.let {
            listPhotosData?.apply {
                val currentItem = this[row]
                val urlPhoto = currentItem.urls?.regular?.toUri()
                val userUrlPhoto = currentItem.user?.profileImage?.medium
                it.usernameLabel.text = currentItem.user?.username

                if (currentItem.user?.bio == null) it.descriptionLabel.visibility = View.GONE
                else {
                    it.descriptionLabel.visibility = View.VISIBLE
                    it.descriptionLabel.text = currentItem.user?.bio
                }

                it.sumLikes.text = currentItem.likes.toString()
                it.descriptionLabel.text = currentItem.user?.bio
                it.imageHolder.loadImage(urlPhoto)
                it.userProfileImageHolder.loadImage(userUrlPhoto)
            }
        }
    }

    override fun registerCell(parent: ViewGroup): ViewBinding {
        return CellPhotosUserHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onItemSelected(row: Int, identifier: String) {

    }
}