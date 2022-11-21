package com.tibz.lpsimulation.activities.tabbar.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.AssetsListViewController
import com.tibz.lpsimulation.activities.list.SectionListViewController
import com.tibz.lpsimulation.activities.list.dataClass.SectionDataObject
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.common.helper.LPConstants
import com.tibz.lpsimulation.databinding.HomeFragmentsOwnedPropertyBinding

class HomePropertyFragmentView(private val parent: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: HomeFragmentsOwnedPropertyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = HomeFragmentsOwnedPropertyBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.ownedPropertyContainer.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerOwnedProperty
            parent.navigation(AssetsListViewController())
        }
        viewBinding.ownedVehicleContainer.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerOwnedVehicle
            parent.navigation(AssetsListViewController())
        }
    }
}