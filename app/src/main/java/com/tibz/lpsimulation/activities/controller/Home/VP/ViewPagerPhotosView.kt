package com.tibz.lpsimulation.activities.controller.Home.VP

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.activities.reusable.collectionView.CollectionViewDataSource
import com.tibz.lpsimulation.activities.reusable.collectionView.CollectionViewImp
import com.tibz.lpsimulation.activities.reusable.views.LPCalendarView
import com.tibz.lpsimulation.common.helper.LPConstants
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.CellPhotosUserHomeBinding
import com.tibz.lpsimulation.databinding.CompCalendarNumberBinding
import com.tibz.lpsimulation.databinding.VpHomeDashboardBinding
import com.tibz.lpsimulation.databinding.BottomSheetUnsplashUserBinding
import androidx.recyclerview.widget.RecyclerView
import com.tibz.lpsimulation.activities.controller.Home.HomeVCDelegate
import com.tibz.lpsimulation.activities.controller.profile.ProfileVC
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.common.extension.*
import com.tibz.lpsimulation.common.extension.BindingExt.Companion.setCheckBoxAt
import com.tibz.lpsimulation.common.extension.ContextExt.Companion.hideKeyboard
import com.tibz.lpsimulation.common.extension.EditTextExt.Companion.interceptBackButton
import com.tibz.lpsimulation.common.helper.LPPermissionManager
import com.tibz.lpsimulation.common.network.unsplash.UnsplashDownloader
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import com.tibz.lpsimulation.databinding.BottomSheetPermissionBinding
import com.tibz.lpsimulation.databinding.BottomSheetUnsplashDownloadBinding
import com.tibz.lpsimulation.databinding.CellPhotosSectionCategoryBinding
import java.lang.ref.WeakReference

class ViewPagerPhotosView(
    private val holder: AppCompatActivity
): Fragment(), CollectionViewDataSource, BottomSheetProtocol {
    var homeDelegate: WeakReference<HomeVCDelegate>? = null
    private var downloader = UnsplashDownloader(holder)
    private var heightRoot: Int = -1
    private var listPhotosData: MutableList<UnsplashPhotos.Response.Details> = mutableListOf()
    private lateinit var categoryDS: CollectionViewImp<String>
    private lateinit var listPhotoDS: CollectionViewImp<UnsplashPhotos.Response.Details>
    private lateinit var vBinding: VpHomeDashboardBinding
    private lateinit var viewModel: HomeTabbarViewModel
    private lateinit var bottomSheetDownloader: BottomSheetUnsplashDownloadBinding
    private lateinit var bottomSheetDownloaderView: BottomSheetView
    private lateinit var bottomSheetPermission: BottomSheetPermissionBinding
    private lateinit var bottomSheetPermissionView: BottomSheetView
    private val vpSectionColl: RecyclerView
    get() = vBinding.moneyInvestedContainer.sectionCategories
    private val vpPhotosColl: RecyclerView
    get() = vBinding.moneyInvestedContainer.listHistoryTransaction
    private var listOfCategories: MutableList<String> = mutableListOf(
        "Filter", "Travel", "Nature", "Street Photography", "Texture & Pattern", "Animals", "Film"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.vp_home_dashboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomSheetDownloader = BottomSheetUnsplashDownloadBinding.inflate(this.layoutInflater)
        bottomSheetPermission = BottomSheetPermissionBinding.inflate(this.layoutInflater)
        vBinding = VpHomeDashboardBinding.bind(view)
        viewModel = HomeTabbarViewModel()
        setupDataSources()
        setupUI()
        bindUI()
        onCreatedFunc()
    }
    private fun onCreatedFunc(nextPage: Boolean = false) {
        if (!viewModel.commitOnSearch)
            loadAllPhotos(nextPage)
        else
            searchPhotos(nextPage)
    }
    private fun loadAllPhotos(nextPage: Boolean = false) {
        if (!nextPage)
            homeDelegate?.get()?.showLoader()

        viewModel.getAllPhotos(nextPage) {
            homeDelegate?.get()?.hideLoader()
            if (it == null) return@getAllPhotos
            val indexBefore = this.listPhotosData.size
            this.listPhotosData
                .addAll(it.toMutableList())
            listPhotoDS
                .notifyItemRangeChanged(
                    indexBefore, this.listPhotosData.size
                )
        }
    }
    private fun searchPhotos(nextPage: Boolean = false) {
        if (!nextPage)
            homeDelegate?.get()?.showLoader()

        viewModel.getPhotos(nextPage) {
            homeDelegate?.get()?.hideLoader()
            if (it?.result == null) return@getPhotos

            if (viewModel.uPage == 1)
                setDataSourcePhotos()

            val indexBefore = this.listPhotosData.size
            this.listPhotosData
                .addAll(it.result!!.toMutableList())
            listPhotoDS
                .notifyItemRangeChanged(
                    indexBefore, this.listPhotosData.size
                )
        }
    }
    private fun setupUI() {

        bottomSheetDownloaderView = BottomSheetView.create(
            this.requireContext(), bottomSheetDownloader, this)
        bottomSheetPermissionView = BottomSheetView.create(
            this.requireContext(), bottomSheetPermission, this)

        setupDataSources()
    }
    /*
        ==========================
        Where The TextField Gets initiated!
        ==========================
    */
    private fun bindUI() {
        vBinding.navigationBar.inputSearch.setOnFocusChangeListener { view, b ->
            if (b) {
                heightRoot = vBinding.root.height
                vBinding.moneyInvestedContainer.listHistoryTransaction.animateAlphaTo(0f)
                vBinding.navigationBar.inputSearch.interceptBackButton(
                    vBinding.root, { doSearch() },
                    { heightInfo ->
                        if (heightInfo == heightRoot)
                            doSearch()
                    }
                )
            }
            else {
                vBinding.moneyInvestedContainer.listHistoryTransaction.animateAlphaTo(1f)
            }
        }
        vBinding.navigationBar.inputSearch.addTextChangedListener {
            if (it.toString() != "")
                vBinding.navigationBar.inputSearchButtong.text = "arrow-right"
            else
                vBinding.navigationBar.inputSearchButtong.text = "search"
        }
        vBinding.navigationBar.inputSearchButtong.setOnClickListener {
            doSearch()
        }
    }
    private fun doSearch() {
        // Remove Keyboard
        this.requireContext().hideKeyboard(vBinding.root)
        // Clear Focus
        val viewInput = vBinding.navigationBar.inputSearch
        val inputText = viewInput.text.toString()
        viewInput.clearFocus()
        if (viewModel.searchPhotosQuery == inputText)
            return
        listPhotosData = mutableListOf()
        viewModel.uPage = 1
        viewModel.searchPhotosQuery = inputText
        onCreatedFunc(false)
    }
    /*
        ==========================
        Setting up data integration
        ==========================
    */
    private fun setupDataSources() {
        setDataSourcePhotos()
        categoryDS = CollectionViewImp(
            listOfCategories,
            this,
            LPConstants.CollectionID.categoriesCell)
        this.context?.let {
            vpPhotosColl.layoutManager = LinearLayoutManager(it)
            vpSectionColl.layoutManager = LinearLayoutManager(it).apply {
                this.orientation = RecyclerView.HORIZONTAL
            }
            vpSectionColl.adapter = categoryDS
        }
    }
    private fun setDataSourcePhotos() {
        listPhotoDS = CollectionViewImp(
            listPhotosData,
            this,
            LPConstants.CollectionID.listsPhotoCell)
        vpPhotosColl.adapter = listPhotoDS
    }
    /*
        ==========================
        Collection View Integration
        ==========================
    */
    override fun onItemShowing(id: String, row: Int, item: ViewBinding) {
        when (id) {
            LPConstants.CollectionID.categoriesCell -> {
                categoryUserBindingTransaction(row, item)
            }
            else -> {
                photosUserBindingTransaction(row, item)
            }
        }
    }
    override fun registerCell(id: String, parent: ViewGroup): ViewBinding {
        when (id) {
            LPConstants.CollectionID.categoriesCell -> {
                return CellPhotosSectionCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> {
                return CellPhotosUserHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        }
    }
    override fun onItemSelected(id: String, row: Int, identifier: String) {
        when (id) {
            LPConstants.CollectionID.categoriesCell -> {
                categoryOnTap(row)
            }
        }
    }
    /*
        ==================================
        This is where the view gets initiated
        ==================================
     */
    @SuppressLint("SetTextI18n")
    private fun photosUserBindingTransaction(row: Int, item: ViewBinding) {
        (item as? CellPhotosUserHomeBinding)?.let {
            listPhotosData.apply {
                if (row > this.size)
                    return
                // apply pagination
                if (row >= (this.size - 1)) {
                    Log.d("Log Transaction", "Supposed to be reload another")
                    this@ViewPagerPhotosView.onCreatedFunc(true)
                }
                val currentItem = this[row]
                val urlPhoto = currentItem.urls?.regular?.toUri()
                val userUrlPhoto = currentItem.user?.profileImage?.medium

                if (row == 0)
                    it.separatorTop.visibility = View.VISIBLE
                else
                    it.separatorTop.visibility = View.GONE

                it.usernameLabel.text = "@${currentItem.user?.username}"
                if (currentItem.user?.bio == null) it.descriptionLabel.visibility = View.GONE
                else {
                    it.descriptionLabel.visibility = View.VISIBLE
                }
                it.sumLikes.text = currentItem.likes.toString()
                it.descriptionLabel.text = currentItem.user?.bio
                it.imageHolder.loadImageWithPreLoad(urlPhoto, 320)//.loadImage(urlPhoto)
                it.userProfileImageHolder.loadImage(userUrlPhoto)
                it.containerUserInformation.setOnClickListener {
                    this@ViewPagerPhotosView.photosOnTap(row)
                }
                it.downloadButton.setOnClickListener { _ ->
                    this@ViewPagerPhotosView
                        .prepareForDownload(currentItem.urls?.full?.toUri(), row)
                }
            }
        }
    }
    private fun categoryUserBindingTransaction(row: Int, item: ViewBinding) {
        (item as? CellPhotosSectionCategoryBinding)?.let {
            listOfCategories.apply {
                val current = this[row]
                if (current == LPConstants.StaticInputLogic.filterName)
                    it.catSymbol.visibility = View.VISIBLE
                else
                    it.catSymbol.visibility = View.GONE

                if (row == 0)
                    it.separatorLeft.visibility = View.VISIBLE
                else
                    it.separatorLeft.visibility = View.GONE

                it.catName.text = current
            }
        }
    }
    /*
        ==========================
        On tap Cell
        ==========================
    */
    private fun categoryOnTap(row: Int) {
        if (row < listOfCategories.size)  {
            val searchInput = listOfCategories[row].toEditable()
            if (searchInput.toString() == LPConstants.StaticInputLogic.filterName) {
                bottomSheetDownloaderView.dialog?.show()
                return
            }
            vBinding.navigationBar.inputSearch.text = listOfCategories[row].toEditable()
            doSearch()
        }
    }
    private fun photosOnTap(row: Int) {
        if (row < listPhotosData.size) {
            val data = listPhotosData[row]
            ProfileVC.username = data.user?.username ?: ""
            ProfileVC.userProfileInformation = data.user
            homeDelegate?.get()?.routeTo(ProfileVC())
        }
    }
    /*
        ==========================
        Download UI Functionallity
        ==========================
    */
    private fun prepareForDownload(url: Uri?, index: Int) {
        setupUIDownloadSheetsView(index)
        homeDelegate?.get()?.setPermission {
            showDialogDownload()
        }
        if (!LPPermissionManager.permissionForStorage(holder)) {
            bottomSheetPermission.allowPermissionButton.setOnClickListener {
                bottomSheetPermissionView.dialog?.dismiss()
                askPermission()
            }
            bottomSheetPermission.ignoreButton.setOnClickListener {
                bottomSheetPermissionView.dialog?.dismiss()
                showDialogDownload()
            }
            bottomSheetPermissionView.dialog?.show()
        } else
            showDialogDownload()
    }
    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.shouldShowRequestPermissionRationale(holder,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            homeDelegate?.get()?.getPermission()?.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            )
        }
        else
            homeDelegate?.get()?.getPermission()?.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            )
    }
    private fun showDialogDownload() {
        bottomSheetDownloader.profileButton.setOnClickListener {
            downloader.downloadImageFor()
            bottomSheetDownloaderView.dialog?.dismiss()
        }
        bottomSheetDownloaderView.dialog?.show()
    }
    private fun setupUIDownloadSheetsView(index: Int) {
        val currentItem = listPhotosData[index]

        bottomSheetDownloader.setCheckBoxAt(this.requireContext(), 0)
        downloader.url = currentItem.urls?.full?.toUri()

        bottomSheetDownloader.bsImage.loadImage(currentItem.urls?.regular)
        bottomSheetDownloader.usernameDownload.text = currentItem.user?.username
        bottomSheetDownloader.full
            .checkDescription.text = "Full Resolution"
        bottomSheetDownloader.regular
            .checkDescription.text = "Regular Resolution"
        bottomSheetDownloader.low
            .checkDescription.text = "Low Resolution"

        bottomSheetDownloader.full.checkboxContainer.setOnClickListener {
            bottomSheetDownloader.setCheckBoxAt(this.requireContext(), 0)
            downloader.url = currentItem.urls?.full?.toUri()
        }
        bottomSheetDownloader.regular.checkboxContainer.setOnClickListener {
            bottomSheetDownloader.setCheckBoxAt(this.requireContext(), 1)
            downloader.url = currentItem.urls?.regular?.toUri()
        }
        bottomSheetDownloader.low.checkboxContainer.setOnClickListener {
            bottomSheetDownloader.setCheckBoxAt(this.requireContext(), 2)
            downloader.url = currentItem.urls?.small?.toUri()
        }
    }
}