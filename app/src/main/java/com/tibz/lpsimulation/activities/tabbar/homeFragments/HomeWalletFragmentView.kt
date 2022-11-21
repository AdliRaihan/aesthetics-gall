package com.tibz.lpsimulation.activities.tabbar.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.databinding.HomeFragmentsWalletUserBinding

class HomeWalletFragmentView(private val parent: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: HomeFragmentsWalletUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = HomeFragmentsWalletUserBinding.inflate(layoutInflater)
        return viewBinding.root
    }
}