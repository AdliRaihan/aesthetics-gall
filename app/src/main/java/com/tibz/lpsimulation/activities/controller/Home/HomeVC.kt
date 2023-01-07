package com.tibz.lpsimulation.activities.controller.Home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.activities.controller.Home.VP.HomeVPCompDashboard
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftPageView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftPager
import com.tibz.lpsimulation.databinding.HomeFragmentsWalletUserBinding
import com.tibz.lpsimulation.databinding.VcHomeBinding

class HomeVC: AppCompatActivity() {
    private lateinit var privateBinding: VcHomeBinding
    private lateinit var homeVCPager: SwiftPager
    private lateinit var bindingone: HomeFragmentsWalletUserBinding
    private lateinit var dashboardVP: HomeVPCompDashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privateBinding = VcHomeBinding.inflate(this.layoutInflater)
        setContentView(privateBinding.root)
        setupUI()
    }

    private fun setupUI() {
        dashboardVP = HomeVPCompDashboard()
        bindingone = HomeFragmentsWalletUserBinding.inflate(this.layoutInflater)
        homeVCPager = SwiftPager(this, arrayOf(
            dashboardVP
            //SwiftPageView(bindingone)
        ))
        privateBinding.viewPageHome.adapter = homeVCPager
        privateBinding.viewPageHome.isUserInputEnabled = false
    }
}