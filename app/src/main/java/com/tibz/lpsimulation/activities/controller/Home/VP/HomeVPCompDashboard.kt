package com.tibz.lpsimulation.activities.controller.Home.VP

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.databinding.VpHomeDashboardBinding

class HomeVPCompDashboard: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.vp_home_dashboard,
            container,
            false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vBinding = VpHomeDashboardBinding.inflate(this.layoutInflater)
    }
}