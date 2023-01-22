package com.tibz.lpsimulation.activities.controller.Home.VP

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.view.*
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
import com.tibz.lpsimulation.common.extension.ContextExt.Companion.hideKeyboard
import com.tibz.lpsimulation.common.extension.EditTextExt.Companion.interceptBackButton
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import com.tibz.lpsimulation.databinding.CellPhotosSectionCategoryBinding
import java.lang.ref.WeakReference

class ViewPagerPhotosView: Fragment(), CollectionViewDataSource, BottomSheetProtocol {

    var homeDelegate: WeakReference<HomeVCDelegate>? = null
    private lateinit var categoryDS: CollectionViewImp<String>
    private lateinit var listPhotoDS: CollectionViewImp<UnsplashPhotos.Response.Details>

    private var listPhotosData: MutableList<UnsplashPhotos.Response.Details> = mutableListOf()
    private var listSearchedPhotos: MutableList<UnsplashSearchPhotos.Details> = mutableListOf()
    private var listOfCategories: MutableList<String> = mutableListOf(
        "Filter", "Travel", "Nature", "Street Photography", "Texture & Pattern", "Animals", "Film"
    )

    private lateinit var vBinding: VpHomeDashboardBinding
    private lateinit var vbBottomSheet: BottomSheetUnsplashUserBinding
    private lateinit var viewModel: HomeTabbarViewModel
    private lateinit var bottomSheet: BottomSheetView

    // list of computed properties so that I don't have to trailing function too long
    private var heightRoot: Int = -1
    private val vpSectionColl: RecyclerView
    get() = vBinding.moneyInvestedContainer.sectionCategories

    private val vpPhotosColl: RecyclerView
    get() = vBinding.moneyInvestedContainer.listHistoryTransaction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.vp_home_dashboard, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vBinding = VpHomeDashboardBinding.bind(view)
        vbBottomSheet = BottomSheetUnsplashUserBinding.inflate(this.layoutInflater)
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
            if (it == null) return@getAllPhotos
            val indexBefore = this.listPhotosData.size
            this.listPhotosData
                .addAll(it.toMutableList())
            listPhotoDS
                .notifyItemRangeChanged(
                    indexBefore, this.listPhotosData.size
                )
                homeDelegate?.get()?.hideLoader()
        }
    }
    private fun searchPhotos(nextPage: Boolean = false) {
        if (!nextPage)
            homeDelegate?.get()?.showLoader()

        viewModel.getPhotos(nextPage) {
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
            homeDelegate?.get()?.hideLoader()
        }
    }
    private fun setupUI() {
        bottomSheet = BottomSheetView.create(
            this.requireContext(), vbBottomSheet, this)
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
                bottomSheet.dialog?.show()
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
}