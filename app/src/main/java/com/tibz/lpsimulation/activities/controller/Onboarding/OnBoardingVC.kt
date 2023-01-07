package com.tibz.lpsimulation.activities.controller.Onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.activities.controller.Setup.SetupVC
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.common.extension.boardingAnimate
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashSearchPhotos
import com.tibz.lpsimulation.databinding.BottomSheetUnsplashUserBinding
import com.tibz.lpsimulation.databinding.VcOnboardingBinding

class OnBoardingVC: AppCompatActivity(), BottomSheetProtocol {
    private lateinit var privateBinding: VcOnboardingBinding
    private var viewModel: OnBoardingVM = OnBoardingVM()
    private lateinit var bottomSheet: BottomSheetView
    private lateinit var vbBottomSheet: BottomSheetUnsplashUserBinding
    private var infoDetails: UnsplashPhotos.Response.Details? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privateBinding = VcOnboardingBinding.inflate(this.layoutInflater)
        vbBottomSheet = BottomSheetUnsplashUserBinding.inflate(this.layoutInflater)
        setContentView(privateBinding.root)
        loadData()
        setupUI()
    }
    override fun onResume() {
        super.onResume()
        privateBinding.backgroundImage.boardingAnimate()
    }
    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        bottomSheet = BottomSheetView.create(
            this, vbBottomSheet, this)
        privateBinding.buttonContinue.setOnClickListener {
            this.navigation(SetupVC())
        }
        privateBinding.infoPhoto.setOnClickListener {
            bottomSheet.dialog?.show()
            vbBottomSheet.username.text = "@" + infoDetails?.user?.username
            vbBottomSheet.profileButtonDesc.text = "Visit @${infoDetails?.user?.username}'s profile"
            vbBottomSheet.photosImage.loadImage(infoDetails?.user?.profileImage?.medium)
        }
    }
    private fun loadData() {
        viewModel.getDarkPhotos {
            if (it?.result?.isEmpty() == true) return@getDarkPhotos
            this.infoDetails = it?.result?.random()
            privateBinding.backgroundImage.loadImage(this.infoDetails?.urls?.regular)
        }
    }
}