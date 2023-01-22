package com.tibz.lpsimulation.activities.controller.Home.VP

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BasePermissionManager
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.collectionView.CollectionViewDataSource
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.helper.LPPhotoManager
import com.tibz.lpsimulation.common.helper.LPStorageManager
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.CellPhotosUserHomeBinding
import com.tibz.lpsimulation.databinding.TabbarHomeViewBinding

class HomeTabbarView(parentHolder: AppCompatActivity): Fragment(),
    BottomSheetProtocol {

    private lateinit var viewBinding: TabbarHomeViewBinding
    private lateinit var viewModel: HomeTabbarViewModel
    private lateinit var dataSource: SwiftCollectionView<UnsplashPhotos.Response.Details>

    private val testGet: RecyclerView
    get() = viewBinding.usersPhotoLists
    private val imagePicker: BasePermissionManager = BasePermissionManager(null, this)
    private var userPhoto = LPStorageManager(parentHolder)
    private var listPhotosData: MutableList<UnsplashPhotos.Response.Details> = mutableListOf()

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

        this.context?.let {
            viewBinding.usersPhotoLists.layoutManager = LinearLayoutManager(it)
            viewBinding.usersPhotoLists.adapter = dataSource
        }
    }

    private fun loadData(nextPage: Boolean = false) {
        viewBinding.setProfilePicture.text = "Loading Data ...."
        viewModel.getAllPhotos(nextPage) {
            if (it == null) return@getAllPhotos
            val indexBefore = this.listPhotosData.size
            if (nextPage) {
                this.listPhotosData.addAll(it.toMutableList())
                dataSource.notifyItemRangeChanged(indexBefore, this.listPhotosData.size)
            }
            else {
                this.listPhotosData = it.toMutableList()

                viewBinding.usersPhotoLists.adapter = dataSource
            }

            viewBinding.setProfilePicture.text = "Current Loaded Data ${dataSource.itemCount} (${indexBefore}) (${this.listPhotosData?.size})"
        }
    }

    private fun registerPermission() {
        imagePicker.registerResultGallery {
            it.data?.data?.apply {
                LPPhotoManager.resizeImage(this@HomeTabbarView.context, this)?.let { img ->
//                    photos.add(img)
                }
            }
        }
    }
}